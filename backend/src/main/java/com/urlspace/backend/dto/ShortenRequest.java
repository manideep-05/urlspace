package com.urlspace.backend.dto;

import lombok.Data;

@Data
public class ShortenRequest {
    private String longUrl;
    private Long userId;
}
