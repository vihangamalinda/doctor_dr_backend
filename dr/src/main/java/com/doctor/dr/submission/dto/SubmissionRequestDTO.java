package com.doctor.dr.submission.dto;

import org.springframework.web.multipart.MultipartFile;

public class SubmissionRequestDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final String createdDate;
    private final String createdTime;
    private final boolean isActive;
    private final boolean hasDisease;

    private final MultipartFile multipartFileImage;

    public SubmissionRequestDTO(Long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease, MultipartFile image) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.multipartFileImage = image;
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

    public boolean getIsActive() {
        return isActive;
    }

    public boolean getHasDisease() {
        return hasDisease;
    }

    public MultipartFile getMultipartFileImage() {
        return multipartFileImage;
    }
}
