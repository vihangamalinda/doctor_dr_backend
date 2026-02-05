package com.doctor.dr.disease.stage.dto;

import com.doctor.dr.disease.stage.entity.DiseaseStage;

import java.util.List;

public class DiseaseStageRequestDTO {

    private final String name;
    private final String description;
    private final Integer diseaseLevel;

    public DiseaseStageRequestDTO( String name, String description, Integer diseaseLevel) {
        this.name = name;
        this.description = description;
        this.diseaseLevel = diseaseLevel;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDiseaseLevel() {
        return diseaseLevel;
    }
}
