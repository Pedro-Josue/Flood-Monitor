package com.fiap.floodmonitoring.alert.repository;

import com.fiap.floodmonitoring.alert.model.FloodAlert;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FloodAlertRepository extends JpaRepository<FloodAlert, Long> {

    List<FloodAlert> findAllByOrderByCreatedAtDesc();
}
