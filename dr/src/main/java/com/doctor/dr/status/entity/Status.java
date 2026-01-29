package com.doctor.dr.status.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Status_Table")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;
    private String description;
    @Column(name = "related_table")
    private String relatedTable;
    @Column(name = "is_active")
    private boolean isActive;

    public Status() {
    }

    public Status(Long id, String name, int value, String description, String relatedTable, boolean isActive) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.description = description;
        this.relatedTable = relatedTable;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelatedTable() {
        return relatedTable;
    }

    public void setRelatedTable(String relatedTable) {
        this.relatedTable = relatedTable;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
