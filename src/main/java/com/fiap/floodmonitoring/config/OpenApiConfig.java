package com.fiap.floodmonitoring.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI floodMonitorOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flood Monitor API")
                        .description("""
                                API REST para cadastro e monitoramento de regioes urbanas com risco de enchente.
                                Projeto academico FIAP Global Solution 2026, alinhado ao ODS 9.
                                """)
                        .version("1.0.0")
                )
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .name(BEARER_AUTH)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}
