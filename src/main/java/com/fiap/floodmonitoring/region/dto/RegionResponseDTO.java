package com.fiap.floodmonitoring.region.dto;

import com.fiap.floodmonitoring.region.model.RiskLevel;

public record RegionResponseDTO(
        Long id,
        String name,
        String city,
        String state,
        String neighborhood,
        Double latitude,
        Double longitude,
        String riverName,
        RiskLevel riskLevel,
        Boolean active,
        String description
) {
}
