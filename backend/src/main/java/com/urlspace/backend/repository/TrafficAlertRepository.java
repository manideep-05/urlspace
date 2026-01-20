package com.urlspace.backend.repository;

import com.urlspace.backend.model.TrafficAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrafficAlertRepository extends JpaRepository<TrafficAlert, Long> {
}
