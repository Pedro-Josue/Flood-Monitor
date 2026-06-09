package com.fiap.floodmonitoring.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoCurrentDTO(
        String time,

        @JsonProperty("temperature_2m")
        Double temperature,

        @JsonProperty("relative_humidity_2m")
        Integer humidity,

        @JsonProperty("rain")
        Double rainfall,

        @JsonProperty("weather_code")
        Integer weatherCode
) {
}
