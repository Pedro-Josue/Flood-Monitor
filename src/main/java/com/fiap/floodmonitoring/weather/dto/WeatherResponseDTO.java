package com.fiap.floodmonitoring.weather.dto;

public record WeatherResponseDTO(
        Double rainfallMm,
        Double temperature,
        Integer humidity,
        String condition,
        String source
) {
}
