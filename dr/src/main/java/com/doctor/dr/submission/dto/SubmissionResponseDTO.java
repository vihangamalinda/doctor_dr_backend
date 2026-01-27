package com.doctor.dr.submission.dto;

import com.doctor.dr.submission.entity.Submission;

public class SubmissionResponseDTO {
    private final long submissionId;

    private final String patientReferenceId;
    private final String createdDate;
    private final String createdTime;
    private final boolean isActive;
    private final boolean hasDisease;

    private final long diseaseStageId;

    public SubmissionResponseDTO(long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease,long diseaseStageId) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease =hasDisease;
        this.diseaseStageId =diseaseStageId;
    }

    public SubmissionResponseDTO(Submission submission){
        this.submissionId =submission.getSubmissionId();
        this.patientReferenceId =submission.getPatientReferenceId();
        this.createdDate =submission.getCreatedDate();
        this.createdTime = submission.getCreatedTime();
        this.hasDisease = submission.hasDisease();
        this.isActive =submission.isActive();
        this.diseaseStageId=submission.getDiseaseStage().getId();
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

    public long getDiseaseStageId() {
        return diseaseStageId;
    }
}
