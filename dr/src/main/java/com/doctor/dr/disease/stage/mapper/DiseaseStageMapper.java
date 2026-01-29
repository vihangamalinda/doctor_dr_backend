package com.doctor.dr.disease.stage.mapper;

import com.doctor.dr.disease.stage.dto.DiseaseStageRequestDTO;
import com.doctor.dr.disease.stage.dto.DiseaseStageResponseDTO;
import com.doctor.dr.disease.stage.entity.DiseaseStage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiseaseStageMapper {
    DiseaseStageResponseDTO toDiseaseStageResponseDTO(DiseaseStage diseaseStage);

    DiseaseStage toDiseaseStage(DiseaseStageRequestDTO diseaseStageRequestDTO);

}
