package com.urlspace.backend.controller;

import com.urlspace.backend.repository.UrlAnalyticsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final UrlAnalyticsRepository analyticsRepository;

    @GetMapping("/clicks-per-url")
    public List<Map<String, Object>> clicksPerUrl() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : analyticsRepository.countClicksPerUrl()) {
            Map<String, Object> map = new HashMap<>();
            map.put("shortCode", row[0]);
            map.put("clicks", row[1]);
            result.add(map);
        }

        return result;
    }

    @GetMapping("/clicks-per-day")
    public List<Map<String, Object>> clicksPerDay() {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : analyticsRepository.clicksPerDay()) {
            Map<String, Object> map = new HashMap<>();
            map.put("date", row[0].toString());
            map.put("clicks", row[1]);
            result.add(map);
        }

        return result;
    }
}
