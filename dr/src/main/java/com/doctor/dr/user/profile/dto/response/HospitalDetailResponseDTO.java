package com.doctor.dr.user.profile.dto.response;

public class HospitalDetailResponseDTO {
    private final Long id;
    private final String name;
    private final String emergencyNumber;
    private final LocationDetailResponseDTO location;
    private final boolean isInternalSystem;


    public HospitalDetailResponseDTO(Long id, String name, String emergencyNumber, LocationDetailResponseDTO location, boolean isInternalSystem) {
        this.id = id;
        this.name = name;
        this.emergencyNumber = emergencyNumber;
        this.location = location;
        this.isInternalSystem = isInternalSystem;
    }

    public boolean getIsInternalSystem() {
        return isInternalSystem;
    }

    public Long getId() {
        return id;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public LocationDetailResponseDTO getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
