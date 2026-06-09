package com.fiap.floodmonitoring.alert.dto;

import com.fiap.floodmonitoring.alert.model.AlertSeverity;
import com.fiap.floodmonitoring.alert.model.AlertStatus;
import java.time.LocalDateTime;

public record FloodAlertResponseDTO(
        Long id,
        Long regionId,
        String regionName,
        String title,
        String message,
        AlertSeverity severity,
        AlertStatus status,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {
}
