package com.doctor.dr.submission.mapper;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;
import com.doctor.dr.submission.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SubmissionMapper {
    @Mapping(source = "diseaseStage", target = "diseaseStageId", qualifiedByName = "mapDiseaseStageId")
    SubmissionResponseDTO toSubmissionResponseDTO(Submission submission);

    Submission toSubmission(SubmissionRequestDTO submissionRequestDTO);

    @Named("mapDiseaseStageId")
    default Long mapDiseaseStageId(DiseaseStage diseaseStage) {
        return diseaseStage == null ? null : diseaseStage.getId();
    }
}
