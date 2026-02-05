package com.doctor.dr.user.profile.dto.response;


import java.time.LocalDateTime;

public class UserProfileResponseDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final LocationDetailResponseDTO location;
    private final boolean isActive;
    private final LocalDateTime createdDateTime;

    private final LocalDateTime modifiedDateTime;

    public UserProfileResponseDTO(Long id, String firstName, String lastName, LocationDetailResponseDTO location, boolean isActive, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.isActive = isActive;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocationDetailResponseDTO getLocation() {
        return location;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }
}
