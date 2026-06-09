package com.fiap.floodmonitoring.region.controller;

import com.fiap.floodmonitoring.region.dto.RegionRequestDTO;
import com.fiap.floodmonitoring.region.dto.RegionResponseDTO;
import com.fiap.floodmonitoring.region.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
@Tag(name = "Regioes Monitoradas", description = "CRUD de regioes urbanas com risco de enchente")
@SecurityRequirement(name = "bearerAuth")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    @Operation(summary = "Lista todas as regioes monitoradas")
    public ResponseEntity<List<RegionResponseDTO>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma regiao monitorada por ID")
    public ResponseEntity<RegionResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova regiao monitorada")
    public ResponseEntity<RegionResponseDTO> create(@RequestBody @Valid RegionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma regiao monitorada")
    public ResponseEntity<RegionResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid RegionRequestDTO request
    ) {
        return ResponseEntity.ok(regionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma regiao monitorada")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
