package com.doctor.dr.hospital.dto.request;

import com.doctor.dr.location.entity.Location;
import jakarta.persistence.Column;

public class HospitalRequestDTO {
    private final String name;
    private final LocationDetailRequestDTO location;
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
