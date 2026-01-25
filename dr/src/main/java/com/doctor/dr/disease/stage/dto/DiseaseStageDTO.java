package com.doctor.dr.disease.stage.dto;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import jakarta.persistence.Column;

public class DiseaseStageDTO {
    private long id;

    private String name;
    private String description;

    private boolean isActive;

    public DiseaseStageDTO() {
    }

    public DiseaseStageDTO(long id, String name, String description, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

    public DiseaseStageDTO(DiseaseStage diseaseStage){
        this.id = diseaseStage.getId();
        this.name = diseaseStage.getName();
        this.description = diseaseStage.getDescription();
        this.isActive = diseaseStage.isActive();
    }

    public long getId() {
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
}
