package com.fiap.floodmonitoring.risk.repository;

import com.fiap.floodmonitoring.risk.model.RiskAnalysisRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskAnalysisRecordRepository extends JpaRepository<RiskAnalysisRecord, Long> {

    List<RiskAnalysisRecord> findByRegionIdOrderByAnalyzedAtDesc(Long regionId);
}
