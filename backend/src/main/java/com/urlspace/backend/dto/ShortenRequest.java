package com.urlspace.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenRequest {
    @NotBlank
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL")
    private String longUrl;
    private Long userId;
}
