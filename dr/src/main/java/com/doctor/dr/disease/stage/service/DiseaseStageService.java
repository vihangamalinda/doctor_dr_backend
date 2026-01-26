package com.doctor.dr.disease.stage.service;

import com.doctor.dr.disease.stage.dto.DiseaseStageDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;

import java.util.List;

public interface DiseaseStageService {
    List<DiseaseStageDTO> getAll();

    DiseaseStageDTO getById(long id);

    void create(DiseaseStageRequestDTO diseaseStageRequestDTO);

    void deleteById(long id);
}
