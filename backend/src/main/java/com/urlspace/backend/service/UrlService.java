package com.urlspace.backend.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.urlspace.backend.model.Url;
import com.urlspace.backend.model.User;
import com.urlspace.backend.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

public class UrlService {

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;
    private final SecureRandom random = new SecureRandom();
    private static final int MAX_RETRIES = 5;
    private final UrlRepository urlRepository;

    public String generateShortCode() {

        StringBuilder shortCode = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            shortCode.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }

        return shortCode.toString();
    }

    public Url createShortUrl(User user, String longUrl) {

        // ✅ Idempotency: same user + same URL → same short code
        Optional<Url> existing = urlRepository.findByUserAndLongUrl(user, longUrl);

        if (existing.isPresent()) {
            return existing.get();
        }

        int attempts = 0;

        while (attempts < MAX_RETRIES) {
            String shortCode = generateShortCode();

            Url url = Url.builder()
                    .shortCode(shortCode)
                    .longUrl(longUrl)
                    .user(user)
                    .build();

            try {
                return urlRepository.save(url);
            } catch (DataIntegrityViolationException ex) {
                // ⚠️ Collision happened → retry
                attempts++;
            }
        }

        // ❌ Fallback error after retries
        throw new RuntimeException(
                "Failed to generate unique short URL. Please try again.");
    }

}
