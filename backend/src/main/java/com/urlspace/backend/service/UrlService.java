package com.urlspace.backend.service;

import java.util.Random;

public class UrlService {

    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;

    public String generateShortCode() {
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            shortCode.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }

        return shortCode.toString();
    }

}
