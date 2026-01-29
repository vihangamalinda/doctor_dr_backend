package com.doctor.dr.disease.stage.entity;

import com.doctor.dr.submission.entity.Submission;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Disease_Stage_Table")
public class DiseaseStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "disease_stage_name")
    private String name;
    @Column(name = "disease_stage_description")
    private String description;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "disease_level")
    private int diseaseLevel;

    @OneToMany(mappedBy = "diseaseStage", fetch = FetchType.LAZY)
    private List<Submission> submissionList = new ArrayList<>();

    public DiseaseStage() {
    }

    public DiseaseStage(long id, String name, String description, boolean isActive, int diseaseLevel) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.diseaseLevel = diseaseLevel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDiseaseLevel() {
        return diseaseLevel;
    }

    public void setDiseaseLevel(int diseaseLevel) {
        this.diseaseLevel = diseaseLevel;
    }

    public List<Submission> getSubmissionList() {
        return submissionList;
    }

    public void setSubmissionList(List<Submission> submissionList) {
        this.submissionList = submissionList;
    }
}
