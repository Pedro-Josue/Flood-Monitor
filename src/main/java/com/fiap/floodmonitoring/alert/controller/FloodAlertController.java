package com.fiap.floodmonitoring.alert.controller;

import com.fiap.floodmonitoring.alert.dto.FloodAlertResponseDTO;
import com.fiap.floodmonitoring.alert.service.FloodAlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alertas de Enchente", description = "Consulta e resolucao de alertas gerados automaticamente")
@SecurityRequirement(name = "bearerAuth")
public class FloodAlertController {

    private final FloodAlertService alertService;

    @GetMapping
    @Operation(summary = "Lista todos os alertas gerados")
    public ResponseEntity<List<FloodAlertResponseDTO>> findAll() {
        return ResponseEntity.ok(alertService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um alerta pelo ID")
    public ResponseEntity<FloodAlertResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.findById(id));
    }

    @PutMapping("/{id}/resolve")
    @Operation(summary = "Marca um alerta como resolvido")
    public ResponseEntity<FloodAlertResponseDTO> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.resolve(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um alerta")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
