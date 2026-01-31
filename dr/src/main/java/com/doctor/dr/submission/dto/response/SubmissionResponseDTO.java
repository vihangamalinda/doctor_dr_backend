package com.doctor.dr.submission.dto.response;

import com.doctor.dr.submission.entity.Submission;

public class SubmissionResponseDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final String createdDate;
    private final String createdTime;
    private final boolean isActive;
    private final boolean hasDisease;
    private final DiseaseStageDetailResponseDTO diseaseStage;
    private final StatusDetailResponseDTO status;
    private final UserProfileDetailResponseDTO userProfile;

    public SubmissionResponseDTO(Long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease, DiseaseStageDetailResponseDTO diseaseStage, StatusDetailResponseDTO status, UserProfileDetailResponseDTO userProfile) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
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
