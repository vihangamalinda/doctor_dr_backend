package com.doctor.dr.submission.service;

import com.doctor.dr.submission.dto.CreateSubmissionDTO;
import com.doctor.dr.submission.dto.SubmissionDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SubmissionService {
    List<SubmissionDTO> getAll();

    SubmissionDTO getSubmissionById(long id);

    void create(CreateSubmissionDTO createSubmissionDTO);

    void deleteById(long id);
}
