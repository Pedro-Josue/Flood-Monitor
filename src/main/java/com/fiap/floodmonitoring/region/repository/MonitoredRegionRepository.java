package com.fiap.floodmonitoring.region.repository;

import com.fiap.floodmonitoring.region.model.MonitoredRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredRegionRepository extends JpaRepository<MonitoredRegion, Long> {
}
