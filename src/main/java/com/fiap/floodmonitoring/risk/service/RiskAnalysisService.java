package com.fiap.floodmonitoring.risk.service;

import com.fiap.floodmonitoring.alert.service.FloodAlertService;
import com.fiap.floodmonitoring.region.model.MonitoredRegion;
import com.fiap.floodmonitoring.region.model.RiskLevel;
import com.fiap.floodmonitoring.region.service.RegionService;
import com.fiap.floodmonitoring.risk.dto.RiskAnalysisResponseDTO;
import com.fiap.floodmonitoring.risk.model.RiskAnalysisRecord;
import com.fiap.floodmonitoring.risk.repository.RiskAnalysisRecordRepository;
import com.fiap.floodmonitoring.weather.dto.WeatherResponseDTO;
import com.fiap.floodmonitoring.weather.service.WeatherService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiskAnalysisService {

    private final RegionService regionService;
    private final WeatherService weatherService;
    private final RiskAnalysisRecordRepository riskRepository;
    private final FloodAlertService alertService;

    @Transactional
    public RiskAnalysisResponseDTO executeAnalysis(Long regionId) {
        MonitoredRegion region = regionService.findEntityById(regionId);
        WeatherResponseDTO weather = weatherService.getCurrentWeather(region.getLatitude(), region.getLongitude());
        RiskLevel calculatedRisk = calculateRisk(region.getRiskLevel(), weather);
        String recommendation = buildRecommendation(calculatedRisk);

        RiskAnalysisRecord record = RiskAnalysisRecord.builder()
                .region(region)
                .analyzedAt(LocalDateTime.now())
                .rainfallMm(weather.rainfallMm())
                .temperature(weather.temperature())
                .humidity(weather.humidity())
                .externalCondition(weather.condition())
                .calculatedRisk(calculatedRisk)
                .recommendation(recommendation)
                .build();

        RiskAnalysisRecord savedRecord = riskRepository.save(record);

        if (calculatedRisk == RiskLevel.HIGH || calculatedRisk == RiskLevel.CRITICAL) {
            alertService.createAutomaticAlert(region, calculatedRisk, recommendation);
        }

        return toResponse(savedRecord);
    }

    @Transactional(readOnly = true)
    public List<RiskAnalysisResponseDTO> findHistoryByRegion(Long regionId) {
        regionService.findEntityById(regionId);
        return riskRepository.findByRegionIdOrderByAnalyzedAtDesc(regionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private RiskLevel calculateRisk(RiskLevel baseRisk, WeatherResponseDTO weather) {
        RiskLevel rainfallRisk = calculateRainfallRisk(weather.rainfallMm());
        RiskLevel result = highest(baseRisk, rainfallRisk);

        boolean highBaseRisk = baseRisk == RiskLevel.HIGH || baseRisk == RiskLevel.CRITICAL;
        if (highBaseRisk && rainfallRisk.ordinal() >= RiskLevel.MEDIUM.ordinal()) {
            result = increaseOneLevel(result);
        }

        boolean veryHumid = weather.humidity() != null && weather.humidity() >= 85;
        if (veryHumid && weather.rainfallMm() >= 10 && result.ordinal() < RiskLevel.CRITICAL.ordinal()) {
            result = increaseOneLevel(result);
        }

        return result;
    }

    private RiskLevel calculateRainfallRisk(Double rainfallMm) {
        double rainfall = rainfallMm == null ? 0.0 : rainfallMm;
        if (rainfall >= 50.0) {
            return RiskLevel.CRITICAL;
        }
        if (rainfall >= 30.0) {
            return RiskLevel.HIGH;
        }
        if (rainfall >= 10.0) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }

    private RiskLevel highest(RiskLevel first, RiskLevel second) {
        return first.ordinal() >= second.ordinal() ? first : second;
    }

    private RiskLevel increaseOneLevel(RiskLevel riskLevel) {
        int nextOrdinal = Math.min(riskLevel.ordinal() + 1, RiskLevel.CRITICAL.ordinal());
        return RiskLevel.values()[nextOrdinal];
    }

    private String buildRecommendation(RiskLevel riskLevel) {
        return switch (riskLevel) {
            case LOW -> "Manter monitoramento preventivo e atualizar dados da regiao periodicamente.";
            case MEDIUM -> "Acompanhar previsao de chuva e preparar equipes para resposta rapida.";
            case HIGH -> "Comunicar equipes de defesa civil, monitorar vias criticas e preparar orientacoes aos moradores.";
            case CRITICAL -> "Acionar plano de contingencia, priorizar evacuacao preventiva em areas vulneraveis e emitir comunicados oficiais.";
        };
    }

    private RiskAnalysisResponseDTO toResponse(RiskAnalysisRecord record) {
        return new RiskAnalysisResponseDTO(
                record.getId(),
                record.getRegion().getId(),
                record.getRegion().getName(),
                record.getAnalyzedAt(),
                record.getRainfallMm(),
                record.getTemperature(),
                record.getHumidity(),
                record.getExternalCondition(),
                record.getCalculatedRisk(),
                record.getRecommendation()
        );
    }
}
