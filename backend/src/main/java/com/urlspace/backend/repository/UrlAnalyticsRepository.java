package com.urlspace.backend.repository;

import com.urlspace.backend.model.UrlAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlAnalyticsRepository extends JpaRepository<UrlAnalytics, Long> {
}
