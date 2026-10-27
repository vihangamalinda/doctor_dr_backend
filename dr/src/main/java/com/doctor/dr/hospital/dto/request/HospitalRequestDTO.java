package com.doctor.dr.hospital.dto.request;

import com.doctor.dr.location.entity.Location;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HospitalRequestDTO {
    @NotBlank(message = "Name is required.")
    private final String name;
    @NotNull(message = "Location is required.")
    private final LocationDetailRequestDTO location;
    @NotBlank(message = "Emergency number is required.")
    private final String emergencyNumber;
    private final boolean isInternalSystem;

    public HospitalRequestDTO(String name, LocationDetailRequestDTO location, String emergencyNumber, boolean isInternalSystem) {
        this.name = name;
        this.location = location;
        this.emergencyNumber = emergencyNumber;
        this.isInternalSystem = isInternalSystem;
    }

    public String getName() {
        return name;
    }

    public LocationDetailRequestDTO getLocation() {
        return location;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public boolean getIsInternalSystem() {
        return isInternalSystem;
    }
}
