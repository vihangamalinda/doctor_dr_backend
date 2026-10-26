package com.doctor.dr.reported.issue.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReportedIssueRequestDTO {
    @NotBlank(message = "Title is required.")
    private final String title;
    @NotBlank(message = "Description is required.")
    private final String description;
    @NotNull(message = "Created date Time is required.")
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
