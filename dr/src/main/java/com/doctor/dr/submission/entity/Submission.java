package com.doctor.dr.submission.entity;

import com.doctor.dr.disease.stage.entity.DiseaseStage;
import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name="Submission_Table")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long submissionId;
    @Column(name="patient_reference_id")
    private String patientReferenceId;
    @Column(name="created_date")
    private String createdDate;
    @Column(name="created_time")
    private String createdTime;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="has_disease")
    private boolean hasDisease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_stage_id",nullable = true)
    private DiseaseStage diseaseStage;

//    private CustomUser customUser;
//    private Status status;

    public Submission() {
    }

    public Submission(long submissionId, String patientReferenceId, String createdDate, String createdTime, boolean isActive, boolean hasDisease,DiseaseStage diseaseStage) {
        this.submissionId = submissionId;
        this.patientReferenceId = patientReferenceId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.isActive = isActive;
        this.hasDisease = hasDisease;
        this.diseaseStage =diseaseStage;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public DiseaseStage getDiseaseStage() {
        return diseaseStage;
    }

    public void setDiseaseStage(DiseaseStage diseaseStage) {
        this.diseaseStage = diseaseStage;
    }

    public boolean hasDisease() {
        return hasDisease;
    }

    public void setHasDisease(boolean hasDisease) {
        this.hasDisease = hasDisease;
    }
}
