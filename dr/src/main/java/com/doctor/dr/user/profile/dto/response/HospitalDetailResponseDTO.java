package com.doctor.dr.user.profile.dto.response;

public class HospitalDetailResponseDTO {
    private final Long id;
    private final String name;
    private final String emergencyNumber;
    private final LocationDetailResponseDTO location;


    public HospitalDetailResponseDTO(Long id, String name, String emergencyNumber, LocationDetailResponseDTO location) {
        this.id = id;
        this.name = name;
        this.emergencyNumber = emergencyNumber;
        this.location = location;
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
