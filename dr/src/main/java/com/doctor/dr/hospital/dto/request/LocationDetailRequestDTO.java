package com.doctor.dr.hospital.dto.request;

public class LocationDetailRequestDTO {
    private final String locationIndex;
    private  final String road;
    private final String city;
    private final String country;

    private final String postalCode ;

    public LocationDetailRequestDTO(String locationIndex, String road, String city, String country, String postalCode) {
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
