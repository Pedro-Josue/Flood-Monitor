package com.fiap.floodmonitoring.region.dto;

import com.fiap.floodmonitoring.region.model.RiskLevel;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegionRequestDTO(
        @NotBlank(message = "Nome da regiao e obrigatorio.")
        @Size(max = 120, message = "Nome deve ter no maximo 120 caracteres.")
        String name,

        @NotBlank(message = "Cidade e obrigatoria.")
        @Size(max = 100, message = "Cidade deve ter no maximo 100 caracteres.")
        String city,

        @NotBlank(message = "Estado e obrigatorio.")
        @Size(min = 2, max = 2, message = "Estado deve usar a sigla com 2 caracteres.")
        String state,

        @NotBlank(message = "Bairro e obrigatorio.")
        @Size(max = 120, message = "Bairro deve ter no maximo 120 caracteres.")
        String neighborhood,

        @NotNull(message = "Latitude e obrigatoria.")
        @DecimalMin(value = "-90.0", message = "Latitude deve ser maior ou igual a -90.")
        @DecimalMax(value = "90.0", message = "Latitude deve ser menor ou igual a 90.")
        Double latitude,

        @NotNull(message = "Longitude e obrigatoria.")
        @DecimalMin(value = "-180.0", message = "Longitude deve ser maior ou igual a -180.")
        @DecimalMax(value = "180.0", message = "Longitude deve ser menor ou igual a 180.")
        Double longitude,

        @Size(max = 120, message = "Nome do rio deve ter no maximo 120 caracteres.")
        String riverName,

        @NotNull(message = "Nivel de risco base e obrigatorio.")
        RiskLevel riskLevel,

        @NotNull(message = "Status ativo e obrigatorio.")
        Boolean active,

        @Size(max = 500, message = "Descricao deve ter no maximo 500 caracteres.")
        String description
) {
}
