package com.doctor.dr.requested.help.dto.request;

import com.doctor.dr.status.entity.Status;
import com.doctor.dr.user.profile.entity.UserProfile;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class RequestedHelpRequestDTO {
    private final String title;
    private final String description;
    private final LocalDateTime createdDateTime;
    private final UserProfileDetailRequestDTO createdByUserProfile;

    public RequestedHelpRequestDTO(String title, String description, LocalDateTime createdDateTime, UserProfileDetailRequestDTO createdByUserProfile) {
        this.title = title;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.createdByUserProfile = createdByUserProfile;
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

    public UserProfileDetailRequestDTO getCreatedByUserProfile() {
        return createdByUserProfile;
    }
}
