package com.doctor.dr.user.profile.entity;

import com.doctor.dr.hospital.entity.Hospital;
import com.doctor.dr.location.entity.Location;
import com.doctor.dr.reported.issue.entity.ReportedIssue;
import com.doctor.dr.requested.help.entity.RequestedHelp;
import com.doctor.dr.submission.entity.Submission;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User_Profile_Table")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;
    @Column(name = "modified_date_time")
    private LocalDateTime modifiedDateTime;

    @ManyToOne()
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @OneToMany(mappedBy = "userProfile")
    private List<Submission> submissionList = new ArrayList<>();

    @OneToMany()
    private List<ReportedIssue> reportedIssueList = new ArrayList<>();

    @OneToMany(mappedBy = "createdByUserProfile")
    private List<RequestedHelp> createdRequests = new ArrayList<>();

    @OneToMany(mappedBy = "reviewedByUserProfile")
    private List<RequestedHelp> reviewedRequests= new ArrayList<>();


    public UserProfile() {
    }

    public UserProfile(Long id, String firstName, String lastName, Location location, boolean isActive, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,Hospital hospital) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.isActive = isActive;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
        this.hospital =hospital;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

}
