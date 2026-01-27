package com.doctor.dr.disease.stage.dto;

import com.doctor.dr.disease.stage.entity.DiseaseStage;

public class DiseaseStageResponseDTO {
    private long id;

    private String name;
    private String description;

    private boolean isActive;
    private int diseaseLevel;


    public DiseaseStageResponseDTO(long id, String name, String description, boolean isActive,int diseaseLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.diseaseLevel=diseaseLevel;
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

    public int getDiseaseLevel() {
        return diseaseLevel;
    }
}
