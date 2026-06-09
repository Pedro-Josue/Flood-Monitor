package com.fiap.floodmonitoring.weather.service;

import com.fiap.floodmonitoring.shared.exception.ExternalApiException;
import com.fiap.floodmonitoring.weather.client.OpenMeteoClient;
import com.fiap.floodmonitoring.weather.dto.OpenMeteoCurrentDTO;
import com.fiap.floodmonitoring.weather.dto.OpenMeteoResponseDTO;
import com.fiap.floodmonitoring.weather.dto.WeatherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private static final String SOURCE = "Open-Meteo";

    private final OpenMeteoClient openMeteoClient;

    public WeatherResponseDTO getCurrentWeather(Double latitude, Double longitude) {
        OpenMeteoResponseDTO response = openMeteoClient.getCurrentWeather(latitude, longitude);
        if (response == null || response.current() == null) {
            throw new ExternalApiException("A API externa de clima retornou uma resposta vazia.");
        }

        OpenMeteoCurrentDTO current = response.current();
        return new WeatherResponseDTO(
                valueOrZero(current.rainfall()),
                current.temperature(),
                current.humidity(),
                describeWeatherCode(current.weatherCode()),
                SOURCE
        );
    }

    private Double valueOrZero(Double value) {
        return value == null ? 0.0 : value;
    }

    private String describeWeatherCode(Integer weatherCode) {
        if (weatherCode == null) {
            return "Condicao climatica nao informada";
        }

        return switch (weatherCode) {
            case 0 -> "Ceu limpo";
            case 1, 2, 3 -> "Parcialmente nublado";
            case 45, 48 -> "Neblina";
            case 51, 53, 55, 56, 57 -> "Garoa";
            case 61, 63, 65, 66, 67 -> "Chuva";
            case 71, 73, 75, 77 -> "Neve";
            case 80, 81, 82 -> "Pancadas de chuva";
            case 85, 86 -> "Pancadas de neve";
            case 95, 96, 99 -> "Tempestade";
            default -> "Condicao climatica codigo " + weatherCode;
        };
    }
}
