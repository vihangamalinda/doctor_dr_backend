package com.doctor.dr.submission.dto.response;

public class DiseaseStageDetailResponseDTO {
    private final Long id;

    private final String name;
    private final Integer diseaseLevel;

    public DiseaseStageDetailResponseDTO(Long id, String name, Integer diseaseLevel) {
        this.id = id;
        this.name = name;
        this.diseaseLevel = diseaseLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDiseaseLevel() {
        return diseaseLevel;
    }
}
