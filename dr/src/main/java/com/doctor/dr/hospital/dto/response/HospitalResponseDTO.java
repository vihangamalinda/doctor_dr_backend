package com.doctor.dr.hospital.dto.response;

import com.doctor.dr.location.entity.Location;
import jakarta.persistence.Column;

public class HospitalResponseDTO {
    private final Long id;
    private final String name;
    private final LocationDetailResponseDTO location;
    private final String emergencyNumber;
    private final boolean isActive;

    public HospitalResponseDTO(Long id, String name, LocationDetailResponseDTO location, String emergencyNumber,boolean isActive) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.emergencyNumber = emergencyNumber;
        this.isActive=isActive;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocationDetailResponseDTO getLocation() {
        return location;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public boolean isActive() {
        return isActive;
    }
}
