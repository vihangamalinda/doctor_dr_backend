package com.doctor.dr.contact.entity;

import com.doctor.dr.requested.help.entity.RequestedHelp;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Contact_Table")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="contact_number")
    private String contactNumber;
    @Column(name="is_active")
    private boolean isActive;
    @OneToMany()
    List<RequestedHelp> requestedHelpList = new ArrayList<>();
    public Contact() {
    }

    public Contact(Long id, String firstName, String lastName, String contactNumber,boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.isActive=isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public List<RequestedHelp> getRequestedHelpList() {
        return requestedHelpList;
    }

    public void setRequestedHelpList(List<RequestedHelp> requestedHelpList) {
        this.requestedHelpList = requestedHelpList;
    }
}
