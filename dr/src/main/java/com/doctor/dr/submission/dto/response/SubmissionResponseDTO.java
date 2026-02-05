package com.doctor.dr.submission.dto.response;

import com.doctor.dr.submission.entity.Submission;

import java.time.LocalDateTime;

public class SubmissionResponseDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final LocalDateTime createdDateTime;
    private final boolean isActive;
    private final boolean hasDisease;
    private final DiseaseStageDetailResponseDTO diseaseStage;
    private final StatusDetailResponseDTO status;
    private final UserProfileDetailResponseDTO userProfile;

    public SubmissionResponseDTO(Long submissionId, String patientReferenceId, LocalDateTime createdDateTime, boolean isActive, boolean hasDisease, DiseaseStageDetailResponseDTO diseaseStage, StatusDetailResponseDTO status, UserProfileDetailResponseDTO userProfile) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDateTime = createdDateTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.diseaseStage =diseaseStage;
        this.status = status;
        this.userProfile = userProfile;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public String getPatientReferenceId() {
        return patientReferenceId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public boolean getHasDisease() {
        return hasDisease;
    }

    public DiseaseStageDetailResponseDTO getDiseaseStage() {
        return diseaseStage;
    }

    public StatusDetailResponseDTO getStatus() {
        return status;
    }

    public UserProfileDetailResponseDTO getUserProfile() {
        return userProfile;
    }
}
