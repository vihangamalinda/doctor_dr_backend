package com.doctor.dr.reported.issue.dto.response;


import java.time.LocalDateTime;

public class ReportedIssueResponseDTO {
    private final Long id;
    private final String title;
    private final String description;
    private final LocalDateTime createdDateTime;

    private final boolean isActive;
    private final UserProfileDetailResponseDTO userProfile;

    private final StatusDetailResponseDTO status;

    public ReportedIssueResponseDTO(Long id, String title, String description, LocalDateTime createdDateTime, boolean isActive, UserProfileDetailResponseDTO userProfile, StatusDetailResponseDTO status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.isActive = isActive;
        this.userProfile = userProfile;
        this.status = status;
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

    public boolean getIsActive() {
        return isActive;
    }

    public UserProfileDetailResponseDTO getUserProfile() {
        return userProfile;
    }

    public StatusDetailResponseDTO getStatus() {
        return status;
    }
}
