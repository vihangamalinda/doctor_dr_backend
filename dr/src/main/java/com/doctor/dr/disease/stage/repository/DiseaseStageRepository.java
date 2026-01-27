package com.doctor.dr.disease.stage.repository;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseStageRepository extends JpaRepository<DiseaseStage, Long> {
    List<DiseaseStage> findAllByIsActiveTrue();
}
