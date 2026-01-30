package com.doctor.dr.hospital.entity;

import com.doctor.dr.location.entity.Location;
import jakarta.persistence.*;

@Entity
@Table(name = "Hospital_Table")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "emergency_number")
    private String emergencyNumber;
    @Column(name = "is_active")
    private boolean isActive;

    public Hospital() {
    }

    public Hospital(Long id, String name, Location location, String emergencyNumber, boolean isActive) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.emergencyNumber = emergencyNumber;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
