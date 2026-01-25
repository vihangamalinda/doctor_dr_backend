package com.doctor.dr.disease.stage.service;

import com.doctor.dr.disease.stage.dto.DiseaseStageDTO;

import java.util.List;

public interface DiseaseStageService {
    List<DiseaseStageDTO> getAll();

    DiseaseStageDTO getById(long id);

    void create(DiseaseStageDTO diseaseStageDTO);

    void deleteById(long id);
}
