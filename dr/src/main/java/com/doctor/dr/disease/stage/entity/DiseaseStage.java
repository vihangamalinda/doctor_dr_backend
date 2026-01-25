package com.doctor.dr.disease.stage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Disease_Stage_Table")
public class DiseaseStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "disease_stage_name")
    private String name;
    @Column(name = "disease_stage_description")
    private String description;
    @Column(name = "is_active")
    private boolean isActive;

    public DiseaseStage() {
    }

    public DiseaseStage(long id, String name, String description, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
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
}
