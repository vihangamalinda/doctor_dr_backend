package com.doctor.dr.contact.dto.request;

import jakarta.persistence.Column;

public class ContactRequestDTO {
    private final String firstName;

    private final String lastName;

    private final String contactNumber;

    public ContactRequestDTO(String firstName, String lastName, String contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
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
}
