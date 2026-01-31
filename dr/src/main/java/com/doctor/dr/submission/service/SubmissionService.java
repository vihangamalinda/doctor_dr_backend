package com.doctor.dr.submission.service;

import com.doctor.dr.submission.dto.request.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.response.SubmissionResponseDTO;

import java.util.List;


public interface SubmissionService {
    List<SubmissionResponseDTO> getAll();

    SubmissionResponseDTO getSubmissionById(long id);

    void create(SubmissionRequestDTO submissionRequestDTO);

    void deleteById(long id);

    List<SubmissionResponseDTO> getSubmissionByDiseaseStageId(long id);

    List<SubmissionResponseDTO> getSubmissionsByUserProfileId(long userProfileId);

}
