package com.fiap.floodmonitoring.risk.model;

import com.fiap.floodmonitoring.region.model.MonitoredRegion;
import com.fiap.floodmonitoring.region.model.RiskLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "risk_analysis_records")
public class RiskAnalysisRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private MonitoredRegion region;

    @Column(name = "analyzed_at", nullable = false)
    private LocalDateTime analyzedAt;

    @Column(name = "rainfall_mm", nullable = false)
    private Double rainfallMm;

    private Double temperature;

    private Integer humidity;

    @Column(name = "external_condition", length = 120)
    private String externalCondition;

    @Enumerated(EnumType.STRING)
    @Column(name = "calculated_risk", nullable = false, length = 20)
    private RiskLevel calculatedRisk;

    @Column(nullable = false, length = 500)
    private String recommendation;
}
