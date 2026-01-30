package com.doctor.dr.user.profile.dto.response;

public class LocationDetailResponseDTO {
    private final Long id;
    private final String address;
    private final String postalCode;
    private final boolean isActive;

    public LocationDetailResponseDTO(Long id, String address, String postalCode, boolean isActive) {
        this.id = id;
        this.address = address;
        this.postalCode = postalCode;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
