package com.doctor.dr.submission.service;

import com.doctor.dr.submission.dto.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.SubmissionResponseDTO;

import java.util.List;


public interface SubmissionService {
    List<SubmissionResponseDTO> getAll();

    SubmissionResponseDTO getSubmissionById(long id);

    void create(SubmissionRequestDTO submissionRequestDTO);

    void deleteById(long id);
}
