package com.doctor.dr.submission.dto.response;

import com.doctor.dr.submission.entity.Submission;

public class SubmissionResponseDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final String createdDate;
    private final String createdTime;
    private final boolean isActive;
    private final boolean hasDisease;

    private final Long diseaseStageId;

    public SubmissionResponseDTO(Long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease, Long diseaseStageId) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.diseaseStageId = diseaseStageId;
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

    public boolean hasDisease() {
        return hasDisease;
    }

    public Long getDiseaseStageId() {
        return diseaseStageId;
    }
}
