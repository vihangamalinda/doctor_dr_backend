package com.doctor.dr.user.profile.dto.response;

public class LocationDetailResponseDTO {
    private final Long id;
    private final String locationIndex;
    private final String road;
    private final String city;
    private final String country;
    private final String postalCode;
    private final boolean isActive;

    public LocationDetailResponseDTO(Long id, String locationIndex, String road, String city, String country, String postalCode, boolean isActive) {
        this.id = id;
        this.locationIndex = locationIndex;
        this.road = road;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getLocationIndex() {
        return locationIndex;
    }

    public String getRoad() {
        return road;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
