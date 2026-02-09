package com.doctor.dr.requested.help.entity;

import com.doctor.dr.status.entity.Status;
import com.doctor.dr.user.profile.entity.UserProfile;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Requested_Help_Table")
public class RequestedHelp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_profile_id", nullable = false)
    private UserProfile createdByUserProfile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
    @Column(name = "feedback_from_operator",length = 500)
    private String feedback;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_user_profile_id", nullable = true)
    private UserProfile reviewedByUserProfile;

    @Column(name = "contact_person_name")
    private String contactName;
    @Column(name = "contact_person_number")
    private String contactNumber;
    @Column(name = "is_active")
    private boolean isActive;

    public RequestedHelp() {
    }

    public RequestedHelp(Long id, String title, String description, LocalDateTime createdDateTime, UserProfile createdByUserProfile, Status status, String feedback, UserProfile reviewedByUserProfile, String contactName, String contactNumber, boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDateTime = createdDateTime;
        this.createdByUserProfile = createdByUserProfile;
        this.status = status;
        this.feedback = feedback;
        this.reviewedByUserProfile = reviewedByUserProfile;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserProfile getCreatedByUserProfile() {
        return createdByUserProfile;
    }

    public void setCreatedByUserProfile(UserProfile createdByUserProfile) {
        this.createdByUserProfile = createdByUserProfile;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UserProfile getReviewedByUserProfile() {
        return reviewedByUserProfile;
    }

    public void setReviewedByUserProfile(UserProfile reviewedByUserProfile) {
        this.reviewedByUserProfile = reviewedByUserProfile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
