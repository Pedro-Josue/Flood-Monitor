package com.fiap.floodmonitoring.region.service;

import com.fiap.floodmonitoring.region.dto.RegionRequestDTO;
import com.fiap.floodmonitoring.region.dto.RegionResponseDTO;
import com.fiap.floodmonitoring.region.model.MonitoredRegion;
import com.fiap.floodmonitoring.region.repository.MonitoredRegionRepository;
import com.fiap.floodmonitoring.shared.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final MonitoredRegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<RegionResponseDTO> findAll() {
        return regionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RegionResponseDTO findById(Long id) {
        return toResponse(findEntityById(id));
    }

    @Transactional(readOnly = true)
    public MonitoredRegion findEntityById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Regiao monitorada nao encontrada."));
    }

    @Transactional
    public RegionResponseDTO create(RegionRequestDTO request) {
        MonitoredRegion region = MonitoredRegion.builder()
                .name(request.name().trim())
                .city(request.city().trim())
                .state(request.state().trim().toUpperCase())
                .neighborhood(request.neighborhood().trim())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .riverName(trimOrNull(request.riverName()))
                .riskLevel(request.riskLevel())
                .active(request.active())
                .description(trimOrNull(request.description()))
                .build();

        return toResponse(regionRepository.save(region));
    }

    @Transactional
    public RegionResponseDTO update(Long id, RegionRequestDTO request) {
        MonitoredRegion region = findEntityById(id);
        region.setName(request.name().trim());
        region.setCity(request.city().trim());
        region.setState(request.state().trim().toUpperCase());
        region.setNeighborhood(request.neighborhood().trim());
        region.setLatitude(request.latitude());
        region.setLongitude(request.longitude());
        region.setRiverName(trimOrNull(request.riverName()));
        region.setRiskLevel(request.riskLevel());
        region.setActive(request.active());
        region.setDescription(trimOrNull(request.description()));

        return toResponse(region);
    }

    @Transactional
    public void delete(Long id) {
        MonitoredRegion region = findEntityById(id);
        regionRepository.delete(region);
    }

    public RegionResponseDTO toResponse(MonitoredRegion region) {
        return new RegionResponseDTO(
                region.getId(),
                region.getName(),
                region.getCity(),
                region.getState(),
                region.getNeighborhood(),
                region.getLatitude(),
                region.getLongitude(),
                region.getRiverName(),
                region.getRiskLevel(),
                region.getActive(),
                region.getDescription()
        );
    }

    private String trimOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
