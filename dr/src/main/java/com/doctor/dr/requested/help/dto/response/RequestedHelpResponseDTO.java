package com.doctor.dr.requested.help.dto.response;

import com.doctor.dr.status.entity.Status;
import com.doctor.dr.user.profile.entity.UserProfile;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class RequestedHelpResponseDTO {
    private final Long id;

    private final String title;

    private final String description;

    private final LocalDateTime createdDateTime;

    private final UserProfileDetailResponseDTO createdByUserProfile;

    private final StatusDetailResponseDTO status;

    private final String feedback;

    private final UserProfileDetailResponseDTO reviewedByUserProfile;

    private final String contactName;

    private final String contactNumber;
    private final boolean isActive;

    public RequestedHelpResponseDTO(Long id, String title, String description, LocalDateTime createdDateTime, UserProfileDetailResponseDTO createdByUserProfile, StatusDetailResponseDTO status, String feedback, UserProfileDetailResponseDTO reviewedByUserProfile, String contactName, String contactNumber, boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.createdByUserProfile = createdByUserProfile;
        this.status = status;
        this.feedback = feedback;
        this.reviewedByUserProfile = reviewedByUserProfile;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public UserProfileDetailResponseDTO getCreatedByUserProfile() {
        return createdByUserProfile;
    }

    public StatusDetailResponseDTO getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public UserProfileDetailResponseDTO getReviewedByUserProfile() {
        return reviewedByUserProfile;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
