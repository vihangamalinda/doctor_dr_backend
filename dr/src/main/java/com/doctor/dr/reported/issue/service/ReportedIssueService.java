package com.doctor.dr.reported.issue.service;

import com.doctor.dr.reported.issue.dto.request.ReportedIssueRequestDTO;
import com.doctor.dr.reported.issue.dto.response.ReportedIssueResponseDTO;

import java.util.List;

public interface ReportedIssueService {
    List<ReportedIssueResponseDTO> getAll();

    ReportedIssueResponseDTO getById(long id);

    void deleteById(long id);

    void create(ReportedIssueRequestDTO reportedIssueRequestDTO);
}
