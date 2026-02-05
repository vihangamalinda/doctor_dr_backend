package com.doctor.dr.reported.issue.service;

import com.doctor.dr.reported.issue.dto.request.ReportedIssueRequestDTO;
import com.doctor.dr.reported.issue.dto.response.ReportedIssueResponseDTO;
import com.doctor.dr.reported.issue.entity.ReportedIssue;
import com.doctor.dr.reported.issue.mapper.ReportedIssueMapper;
import com.doctor.dr.reported.issue.repository.ReportedIssueRepository;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.service.information.StatusInformation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportedIssueServiceImpl implements ReportedIssueService {

    private final ReportedIssueRepository reportedIssueRepository;
    private final ReportedIssueMapper reportedIssueMapper;
    private final StatusInformation statusInformation;

    public ReportedIssueServiceImpl(ReportedIssueRepository reportedIssueRepository, ReportedIssueMapper reportedIssueMapper, StatusInformation statusInformation) {
        this.reportedIssueRepository = reportedIssueRepository;
        this.reportedIssueMapper = reportedIssueMapper;
        this.statusInformation = statusInformation;
    }

    @Override
    public List<ReportedIssueResponseDTO> getAll() {
        List<ReportedIssue> reportedIssueList = this.reportedIssueRepository.findAllByIsActiveTrue();
        return reportedIssueList.stream().map(this.reportedIssueMapper::toReportedIssueResponseDTO).collect(Collectors.toList());
    }

    @Override
    public ReportedIssueResponseDTO getById(long id) {
        ReportedIssue reportedIssue = findById(id);
        return this.reportedIssueMapper.toReportedIssueResponseDTO(reportedIssue);
    }

    private ReportedIssue findById(long id) {
        return this.reportedIssueRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
    }

    @Override
    public void deleteById(long id) {
        ReportedIssue reportedIssue = findById(id);
        if (reportedIssue.getIsActive()) {
            reportedIssue.setIsActive(false);
        }
    }

    @Override
    public void create(ReportedIssueRequestDTO reportedIssueRequestDTO) {
        ReportedIssue reportedIssue = this.reportedIssueMapper.toReportedIssue(reportedIssueRequestDTO);
        reportedIssue.setIsActive(true);
        Status status = getStatusInformationById(10002);
        reportedIssue.setStatus(status);
    }

    @Override
    public List<ReportedIssueResponseDTO> getReportedIssueByUserProfileId(long id) {
        List<ReportedIssue> reportedIssueList = this.reportedIssueRepository.findByUserProfile_id(id);
        return reportedIssueList.stream().map(this.reportedIssueMapper::toReportedIssueResponseDTO).collect(Collectors.toList());
    }

    private Status getStatusInformationById(long id) {
        return this.statusInformation.getStatusById(id);
    }
}
