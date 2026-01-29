package com.doctor.dr.submission.mapper;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.submission.dto.response.DiseaseStageDetailResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "SubmissionDiseaseStageDetailMapperImpl")
public interface DiseaseStageDetailMapper {
    @Named("toDiseaseStageDetailResponseDTO")
    DiseaseStageDetailResponseDTO toDiseaseStageDetailResponseDTO(DiseaseStage diseaseStage);
}
