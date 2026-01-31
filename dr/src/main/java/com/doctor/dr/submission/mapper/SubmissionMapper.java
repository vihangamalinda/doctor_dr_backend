package com.doctor.dr.submission.mapper;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;
import com.doctor.dr.submission.entity.Submission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {StatusDetailMapper.class, DiseaseStageDetailMapper.class, UserProfileDetailMapper.class})
public interface SubmissionMapper {
    @Mapping(source = "diseaseStage", target = "diseaseStage", qualifiedByName = "toDiseaseStageDetailResponseDTO")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusDetailResponseDTO")
    @Mapping(source = "userProfile", target = "userProfile", qualifiedByName = "toUserProfileDetailResponseDTO")
    SubmissionResponseDTO toSubmissionResponseDTO(Submission submission);

    @Mapping(source = "userProfile", target = "userProfile", qualifiedByName = "toUserProfile")
    Submission toSubmission(SubmissionRequestDTO submissionRequestDTO);
}
