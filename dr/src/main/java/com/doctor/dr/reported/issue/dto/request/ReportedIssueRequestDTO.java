package com.doctor.dr.reported.issue.dto.request;


import java.time.LocalDateTime;

public class ReportedIssueRequestDTO {
    private final String title;
    private final String description;
    private final LocalDateTime createdDateTime;

    private final UserProfileDetailRequestDTO userProfile;

    public ReportedIssueRequestDTO( String title, String description, LocalDateTime createdDateTime, UserProfileDetailRequestDTO userProfile) {
        this.title = title;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.userProfile = userProfile;
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

    public UserProfileDetailRequestDTO getUserProfile() {
        return userProfile;
    }
}
