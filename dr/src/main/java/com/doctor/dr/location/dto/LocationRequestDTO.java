package com.doctor.dr.location.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class LocationRequestDTO {
    @NotBlank(message = "Location index required.")
    private final String locationIndex;
    @NotBlank(message = "Road index required.")
    private final String road;
    @NotBlank(message = "City index required.")
    private final String city;
    @NotBlank(message = "Country index required.")
    private final String country;
    @NotBlank(message = "Postal code index required.")
    private final String postalCode;

    public LocationRequestDTO(String locationIndex, String road, String city, String country, String postalCode) {
        this.locationIndex = locationIndex;
        this.road = road;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
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
}
