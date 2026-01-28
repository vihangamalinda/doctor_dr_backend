package com.doctor.dr.location.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Location_Table")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationIndex;
    private  String road;
    private String city;
    private String country;
    @Column(name = "postal_code")
    private String postalCode ;
    @Column(name="is_active")
    private boolean isActive;

    public Location() {
    }

    public Location(Long id, String locationIndex, String road, String city, String country, String postalCode,boolean isActive) {
        this.id = id;
        this.locationIndex = locationIndex;
        this.road = road;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.isActive =isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationIndex() {
        return locationIndex;
    }

    public void setLocationIndex(String locationIndex) {
        this.locationIndex = locationIndex;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
