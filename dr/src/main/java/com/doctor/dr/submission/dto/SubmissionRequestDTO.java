package com.doctor.dr.submission.dto;

import org.springframework.web.multipart.MultipartFile;

public class SubmissionRequestDTO {
    private Long submissionId;

    private String patientReferenceId;
    private String createdDate;
    private String createdTime;
    private boolean isActive;
    private boolean hasDisease;

    private MultipartFile multipartFileImage;

    public SubmissionRequestDTO() {
    }

    public SubmissionRequestDTO(Long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease, MultipartFile image) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.multipartFileImage =image;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public String getPatientReferenceId() {
        return patientReferenceId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean hasDisease() {
        return hasDisease;
    }

    public MultipartFile getMultipartFileImage() {
        return multipartFileImage;
    }
}
