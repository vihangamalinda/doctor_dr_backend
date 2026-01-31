package com.doctor.dr.user.profile.dto.request;


import java.time.LocalDateTime;

public class UserProfileRequestDTO {
    private final String firstName;
    private final String lastName;
    private final LocationDetailRequestDTO location;
    private final LocalDateTime createdDateTime;

    private final LocalDateTime modifiedDateTime;
    private final HospitalDetailRequestDTO hospital;

    public UserProfileRequestDTO(String firstName, String lastName, LocationDetailRequestDTO location, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime, HospitalDetailRequestDTO hospital) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.hospital = hospital;
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
}
