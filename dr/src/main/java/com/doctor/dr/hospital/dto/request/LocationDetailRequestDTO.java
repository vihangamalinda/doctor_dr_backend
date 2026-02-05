package com.doctor.dr.hospital.dto.request;

public class LocationDetailRequestDTO {
    private final String locationIndex;
    private final String road;
    private final String city;
    private final String country;

    private final String postalCode;
    private final boolean isActive;

    public LocationDetailRequestDTO(String locationIndex, String road, String city, String country, String postalCode, boolean isActive) {
        this.locationIndex = locationIndex;
        this.road = road;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.isActive = isActive;
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
