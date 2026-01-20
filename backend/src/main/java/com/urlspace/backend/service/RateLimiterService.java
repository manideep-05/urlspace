package com.urlspace.backend.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final int LIMIT = 10; // Max 10 requests
    private static final int WINDOW_SECONDS = 60; // per 60 seconds

    public boolean isAllowed(String userKey) {
        String key = "rate_limiter:" + userKey;
        Long currentCount = redisTemplate.opsForValue().increment(key);

        if (currentCount == 1) {
            redisTemplate.expire(key, java.time.Duration.ofSeconds(WINDOW_SECONDS));
        }

        return currentCount <= LIMIT;
    }

}
