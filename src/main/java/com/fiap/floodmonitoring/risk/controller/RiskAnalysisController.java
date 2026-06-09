package com.fiap.floodmonitoring.risk.controller;

import com.fiap.floodmonitoring.risk.dto.RiskAnalysisResponseDTO;
import com.fiap.floodmonitoring.risk.service.RiskAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regions/{regionId}/risk-analysis")
@RequiredArgsConstructor
@Tag(name = "Analise de Risco", description = "Execucao e historico de analises de risco de enchente")
@SecurityRequirement(name = "bearerAuth")
public class RiskAnalysisController {

    private final RiskAnalysisService riskAnalysisService;

    @PostMapping
    @Operation(summary = "Executa nova analise de risco para uma regiao")
    public ResponseEntity<RiskAnalysisResponseDTO> executeAnalysis(@PathVariable Long regionId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(riskAnalysisService.executeAnalysis(regionId));
    }

    @GetMapping
    @Operation(summary = "Lista historico de analises de risco de uma regiao")
    public ResponseEntity<List<RiskAnalysisResponseDTO>> findHistory(@PathVariable Long regionId) {
        return ResponseEntity.ok(riskAnalysisService.findHistoryByRegion(regionId));
    }
}
