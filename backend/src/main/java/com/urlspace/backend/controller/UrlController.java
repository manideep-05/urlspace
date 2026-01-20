package com.urlspace.backend.controller;

import com.urlspace.backend.dto.ShortenRequest;
import com.urlspace.backend.model.Url;
import com.urlspace.backend.repository.UrlRepository;
import com.urlspace.backend.service.UrlService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlRepository urlRepository;
    private final UrlService urlService;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/shorten")
    public String shorten(@RequestBody ShortenRequest request) {

        String shortCode = urlService.generateShortCode();

        Url url = Url.builder()
                .shortCode(shortCode)
                .longUrl(request.getLongUrl())
                .userId(request.getUserId())
                .build();

        urlRepository.save(url);

        return "Short URL: http://localhost:9090/" + shortCode;
    }

    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode) {

        String cacheKey = "url:" + shortCode;
        // Check Redis cache first
        String cachedLongUrl = redisTemplate.opsForValue().get(cacheKey);
        if (cachedLongUrl != null) {
            return "Redirect from cache: " + cachedLongUrl;
        }

        // If not found, fetch from DB
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        // Store in Redis cache
        redisTemplate.opsForValue().set(cacheKey, url.getLongUrl());

        return "Redirect from DB: " + url.getLongUrl();
    }
}
