package com.fiap.floodmonitoring.risk.dto;

import com.fiap.floodmonitoring.region.model.RiskLevel;
import java.time.LocalDateTime;

public record RiskAnalysisResponseDTO(
        Long id,
        Long regionId,
        String regionName,
        LocalDateTime analyzedAt,
        Double rainfallMm,
        Double temperature,
        Integer humidity,
        String externalCondition,
        RiskLevel calculatedRisk,
        String recommendation
) {
}
