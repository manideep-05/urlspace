package com.urlspace.backend.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.urlspace.backend.model.UrlAnalytics;
import com.urlspace.backend.repository.UrlAnalyticsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final UrlAnalyticsRepository analyticsRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public void logClick(String shortCode, String ip) {

        String rateKey = "clicks:" + shortCode;

        Long count = redisTemplate.opsForValue().increment(rateKey);

        if (count == 1) {
            redisTemplate.expire(rateKey, Duration.ofMinutes(1));
        }

        if (count > 5) {
            System.out.println(" ALERT: High traffic detected for " + shortCode);
        }

        UrlAnalytics analytics = UrlAnalytics.builder()
                .shortCode(shortCode)
                .ipAddress(ip)
                .timestamp(LocalDateTime.now())
                .build();

        analyticsRepository.save(analytics);
    }

}
