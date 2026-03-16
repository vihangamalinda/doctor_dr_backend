package com.doctor.dr.contact.dto.response;

import jakarta.persistence.Column;

public class ContactResponseDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String contactNumber;
    private final boolean isActive;

    public ContactResponseDTO(Long id, String firstName, String lastName, String contactNumber, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean isActive() {
        return isActive;
    }
}
