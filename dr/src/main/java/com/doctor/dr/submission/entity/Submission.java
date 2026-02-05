package com.doctor.dr.submission.entity;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.user.profile.entity.UserProfile;
import jakarta.annotation.Generated;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Submission_Table")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;
    @Column(name = "patient_reference_id")
    private String patientReferenceId;
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "has_disease")
    private boolean hasDisease;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id",nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_stage_id", nullable = true)
    private DiseaseStage diseaseStage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id",nullable = false)
    private UserProfile userProfile;
//    private Status status;

    public Submission() {
    }

    public Submission(long submissionId, String patientReferenceId, LocalDateTime createdDateTime, boolean isActive, boolean hasDisease, DiseaseStage diseaseStage,UserProfile userProfile) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDateTime = createdDateTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.diseaseStage = diseaseStage;
        this.status = null;
        this.userProfile =userProfile;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public String getPatientReferenceId() {
        return patientReferenceId;
    }

    public void setPatientReferenceId(String patientReferenceId) {
        this.patientReferenceId = patientReferenceId;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public DiseaseStage getDiseaseStage() {
        return diseaseStage;
    }

    public void setDiseaseStage(DiseaseStage diseaseStage) {
        this.diseaseStage = diseaseStage;
    }

    public boolean getHasDisease() {
        return hasDisease;
    }

    public void setHasDisease(boolean hasDisease) {
        this.hasDisease = hasDisease;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
