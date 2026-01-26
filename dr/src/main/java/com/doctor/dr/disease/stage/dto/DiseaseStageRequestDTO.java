package com.doctor.dr.disease.stage.dto;

import com.doctor.dr.disease.stage.entity.DiseaseStage;

import java.util.List;

public class DiseaseStageRequestDTO {
    private long id;

    private String name;
    private String description;

    private boolean isActive;

    public DiseaseStageRequestDTO() {
    }

    public DiseaseStageRequestDTO(long id, String name, String description, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
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
