package com.urlspace.backend.repository;

import com.urlspace.backend.model.UrlAnalytics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UrlAnalyticsRepository extends JpaRepository<UrlAnalytics, Long> {

    @Query("SELECT a.shortCode, COUNT(a) FROM UrlAnalytics a GROUP BY a.shortCode")
    List<Object[]> countClicksPerUrl();

    @Query("""
                SELECT DATE(a.timestamp), COUNT(a)
                FROM UrlAnalytics a
                GROUP BY DATE(a.timestamp)
                ORDER BY DATE(a.timestamp)
            """)
    List<Object[]> clicksPerDay();
}
