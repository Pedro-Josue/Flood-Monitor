package com.fiap.floodmonitoring.alert.service;

import com.fiap.floodmonitoring.alert.dto.FloodAlertResponseDTO;
import com.fiap.floodmonitoring.alert.model.AlertSeverity;
import com.fiap.floodmonitoring.alert.model.AlertStatus;
import com.fiap.floodmonitoring.alert.model.FloodAlert;
import com.fiap.floodmonitoring.alert.repository.FloodAlertRepository;
import com.fiap.floodmonitoring.region.model.MonitoredRegion;
import com.fiap.floodmonitoring.region.model.RiskLevel;
import com.fiap.floodmonitoring.shared.exception.ResourceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FloodAlertService {

    private final FloodAlertRepository alertRepository;

    @Transactional(readOnly = true)
    public List<FloodAlertResponseDTO> findAll() {
        return alertRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FloodAlertResponseDTO findById(Long id) {
        return toResponse(findEntityById(id));
    }

    @Transactional
    public FloodAlertResponseDTO resolve(Long id) {
        FloodAlert alert = findEntityById(id);
        alert.setStatus(AlertStatus.RESOLVED);
        if (alert.getResolvedAt() == null) {
            alert.setResolvedAt(LocalDateTime.now());
        }
        return toResponse(alert);
    }

    @Transactional
    public void delete(Long id) {
        FloodAlert alert = findEntityById(id);
        alertRepository.delete(alert);
    }

    @Transactional
    public FloodAlert createAutomaticAlert(
            MonitoredRegion region,
            RiskLevel calculatedRisk,
            String recommendation
    ) {
        AlertSeverity severity = calculatedRisk == RiskLevel.CRITICAL
                ? AlertSeverity.CRITICAL
                : AlertSeverity.WARNING;

        FloodAlert alert = FloodAlert.builder()
                .region(region)
                .title(buildTitle(calculatedRisk, region.getName()))
                .message(buildMessage(calculatedRisk, region, recommendation))
                .severity(severity)
                .status(AlertStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build();

        return alertRepository.save(alert);
    }

    private FloodAlert findEntityById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta de enchente nao encontrado."));
    }

    private String buildTitle(RiskLevel calculatedRisk, String regionName) {
        return "Risco " + calculatedRisk.name().toLowerCase() + " de enchente em " + regionName;
    }

    private String buildMessage(RiskLevel calculatedRisk, MonitoredRegion region, String recommendation) {
        return "A analise automatica identificou risco " + calculatedRisk
                + " para a regiao " + region.getName()
                + ", em " + region.getCity() + "/" + region.getState()
                + ". Recomendacao: " + recommendation;
    }

    public FloodAlertResponseDTO toResponse(FloodAlert alert) {
        return new FloodAlertResponseDTO(
                alert.getId(),
                alert.getRegion().getId(),
                alert.getRegion().getName(),
                alert.getTitle(),
                alert.getMessage(),
                alert.getSeverity(),
                alert.getStatus(),
                alert.getCreatedAt(),
                alert.getResolvedAt()
        );
    }
}
