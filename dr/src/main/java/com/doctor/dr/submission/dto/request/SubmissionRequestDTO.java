package com.doctor.dr.submission.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class SubmissionRequestDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final String createdDate;
    private final String createdTime;
    private final boolean isActive;
    private final boolean hasDisease;
    private final MultipartFile multipartFileImage;

    private final UserProfileDetailRequestDTO userProfile;

    public SubmissionRequestDTO(Long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease, MultipartFile image, UserProfileDetailRequestDTO userProfile) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.multipartFileImage = image;
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

    public boolean getHasDisease() {
        return hasDisease;
    }

    public MultipartFile getMultipartFileImage() {
        return multipartFileImage;
    }

    public UserProfileDetailRequestDTO getUserProfile() {
        return userProfile;
    }
}
