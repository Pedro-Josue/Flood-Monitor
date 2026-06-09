package com.fiap.floodmonitoring.weather.client;

import com.fiap.floodmonitoring.shared.exception.ExternalApiException;
import com.fiap.floodmonitoring.weather.dto.OpenMeteoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class OpenMeteoClient {

    private final RestClient restClient;

    public OpenMeteoClient(
            RestClient.Builder restClientBuilder,
            @Value("${external.weather.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public OpenMeteoResponseDTO getCurrentWeather(Double latitude, Double longitude) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "temperature_2m,relative_humidity_2m,rain,weather_code")
                            .queryParam("timezone", "auto")
                            .build()
                    )
                    .retrieve()
                    .body(OpenMeteoResponseDTO.class);
        } catch (RestClientException exception) {
            throw new ExternalApiException(
                    "Nao foi possivel consultar a API externa de clima no momento.",
                    exception
            );
        }
    }
}
