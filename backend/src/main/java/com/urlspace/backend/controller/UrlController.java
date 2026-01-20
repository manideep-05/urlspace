package com.urlspace.backend.controller;

import com.urlspace.backend.dto.ShortenRequest;
import com.urlspace.backend.model.Url;
import com.urlspace.backend.model.User;
import com.urlspace.backend.repository.UrlRepository;
import com.urlspace.backend.repository.UserRepository;
import com.urlspace.backend.service.AnalyticsService;
import com.urlspace.backend.service.RateLimiterService;
import com.urlspace.backend.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlRepository urlRepository;
    private final UrlService urlService;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RateLimiterService rateLimiterService;
    private final AnalyticsService analyticsService;

    @PostMapping("/shorten")
    public String shorten(@Valid @RequestBody ShortenRequest request, HttpServletRequest httpRequest) {

        String email = (String) httpRequest.getAttribute("email");

        if (!rateLimiterService.isAllowed(email)) {
            // throw new RuntimeException("Rate limit exceeded. Try again later.");
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");

        }

        String shortCode = urlService.generateShortCode();
        User user = userRepository.findByEmail(email).orElseThrow();

        Optional<Url> existing = urlRepository.findByUserAndLongUrl(user, request.getLongUrl());

        if (existing.isPresent()) {
            shortCode = existing.get().getShortCode();
        }

        Url url = Url.builder()
                .shortCode(shortCode)
                .longUrl(request.getLongUrl())
                // .userId(1L) // Placeholder for user ID
                .userId(request.getUserId())
                .build();

        urlRepository.save(url);

        return "Short URL: http://localhost:9090/" + shortCode;
    }

    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode, HttpServletRequest httpRequest) {

        String cacheKey = "url:" + shortCode;
        String ipAddress = httpRequest.getRemoteAddr();
        analyticsService.logClick(shortCode, ipAddress);
        // Check Redis cache first
        String cachedLongUrl = redisTemplate.opsForValue().get(cacheKey);
        if (cachedLongUrl != null) {
            return "Redirect from cache: " + cachedLongUrl;
        }

        // If not found, fetch from DB
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        // Store in Redis cache
        // redisTemplate.opsForValue().set(cacheKey, url.getLongUrl());
        redisTemplate.opsForValue().set(cacheKey, url.getLongUrl(), Duration.ofHours(24));

        return "Redirect from DB: " + url.getLongUrl();
    }

    @GetMapping("/my")
    public List<Url> myUrls(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        User user = userRepository.findByEmail(email).orElseThrow();
        return urlRepository.findByUser(user);
    }
}
