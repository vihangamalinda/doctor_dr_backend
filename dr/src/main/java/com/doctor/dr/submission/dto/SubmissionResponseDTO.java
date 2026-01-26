package com.doctor.dr.submission.dto;

import com.doctor.dr.submission.entity.Submission;

public class SubmissionResponseDTO {
    private long submissionId;

    private String patientReferenceId;
    private String createdDate;
    private String createdTime;
    private boolean isActive;
    private boolean hasDisease;

    public SubmissionResponseDTO() {
    }

    public SubmissionResponseDTO(long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
    }

    public SubmissionResponseDTO(Submission submission){
        this.submissionId =submission.getSubmissionId();
        this.patientReferenceId =submission.getPatientReferenceId();
        this.createdDate =submission.getCreatedDate();
        this.createdTime = submission.getCreatedTime();
        this.hasDisease = submission.hasDisease();
        this.isActive =submission.isActive();
    }

    public long getSubmissionId() {
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
}
