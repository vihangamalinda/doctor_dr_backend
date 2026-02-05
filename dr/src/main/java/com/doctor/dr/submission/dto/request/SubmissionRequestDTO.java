package com.doctor.dr.submission.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class SubmissionRequestDTO {
    private final Long submissionId;

    private final String patientReferenceId;
    private final LocalDateTime createdDateTime;

    private MultipartFile multipartFileImage;

    private final UserProfileDetailRequestDTO userProfile;

    public SubmissionRequestDTO(Long submissionId, String patientReferenceId, LocalDateTime createdDateTime, MultipartFile image, UserProfileDetailRequestDTO userProfile) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDateTime = createdDateTime;
        this.multipartFileImage = image;
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

    public MultipartFile getMultipartFileImage() {
        return multipartFileImage;
    }

    public void setMultipartFileImage(MultipartFile multipartFileImage) {
        this.multipartFileImage = multipartFileImage;
    }

    public UserProfileDetailRequestDTO getUserProfile() {
        return userProfile;
    }
}
