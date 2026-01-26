package com.doctor.dr.submission.service;

import com.doctor.dr.submission.dto.SubmissionRequestDTO;
import com.doctor.dr.submission.dto.SubmissionDTO;

import java.util.List;


public interface SubmissionService {
    List<SubmissionDTO> getAll();

    SubmissionDTO getSubmissionById(long id);

    void create(SubmissionRequestDTO submissionRequestDTO);

    void deleteById(long id);
}
