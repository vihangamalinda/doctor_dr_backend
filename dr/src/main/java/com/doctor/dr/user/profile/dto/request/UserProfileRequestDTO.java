package com.doctor.dr.user.profile.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UserProfileRequestDTO {
    @NotBlank(message = "First name is required")
    private final String firstName;
    @NotBlank(message = "Last name is required")
    private final String lastName;
    @NotBlank(message = "Location is required")
    private final LocationDetailRequestDTO location;
    @NotBlank(message = "created date time is required")
    private final LocalDateTime createdDateTime;
    @NotBlank(message = "email is required")
    @Email(message = "should be a valid email")
    private final String email;
    @NotNull(message = "modified date time required")
    private final LocalDateTime modifiedDateTime;
    @NotNull(message = "hospital required")
    private final HospitalDetailRequestDTO hospital;
    @NotNull(message = "user internal system information required")
    private final UserInternalSystemInformationRequestDTO userInternalSystemInformation;

    public UserProfileRequestDTO(String firstName, String lastName, LocationDetailRequestDTO location, LocalDateTime createdDateTime, String email, LocalDateTime modifiedDateTime, HospitalDetailRequestDTO hospital, UserInternalSystemInformationRequestDTO userInternalSystemInformation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.createdDateTime = createdDateTime;
        this.email = email;
        this.modifiedDateTime = modifiedDateTime;
        this.hospital = hospital;
        this.userInternalSystemInformation = userInternalSystemInformation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocationDetailRequestDTO getLocation() {
        return location;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public HospitalDetailRequestDTO getHospital() {
        return hospital;
    }

    public UserInternalSystemInformationRequestDTO getUserInternalSystemInformation() {
        return userInternalSystemInformation;
    }

    public String getEmail() {
        return email;
    }
}
