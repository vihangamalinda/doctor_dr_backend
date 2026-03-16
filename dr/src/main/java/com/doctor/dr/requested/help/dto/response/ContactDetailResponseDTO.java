package com.doctor.dr.requested.help.dto.response;

public class ContactDetailResponseDTO {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String contactNumber;
    private final boolean isActive;

    public ContactDetailResponseDTO(Long id, String firstName, String lastName, String contactNumber, boolean isActive) {
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

    public boolean getIsActive() {
        return isActive;
    }
}
