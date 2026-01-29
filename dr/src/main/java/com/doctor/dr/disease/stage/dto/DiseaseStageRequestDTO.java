package com.doctor.dr.disease.stage.dto;

import com.doctor.dr.disease.stage.entity.DiseaseStage;

import java.util.List;

public class DiseaseStageRequestDTO {
    private final Long id;

    private final String name;
    private final String description;

    private final boolean isActive;
    private final Integer diseaseLevel;

    public DiseaseStageRequestDTO(Long id, String name, String description, boolean isActive, Integer diseaseLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.diseaseLevel = diseaseLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public Integer getDiseaseLevel() {
        return diseaseLevel;
    }
}
