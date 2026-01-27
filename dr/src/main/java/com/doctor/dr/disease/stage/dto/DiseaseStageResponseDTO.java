package com.doctor.dr.disease.stage.dto;

import com.doctor.dr.disease.stage.entity.DiseaseStage;

public class DiseaseStageResponseDTO {
    private Long id;

    private String name;
    private String description;

    private boolean isActive;
    private Integer diseaseLevel;


    public DiseaseStageResponseDTO(Long id, String name, String description, boolean isActive,Integer diseaseLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.diseaseLevel=diseaseLevel;
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
