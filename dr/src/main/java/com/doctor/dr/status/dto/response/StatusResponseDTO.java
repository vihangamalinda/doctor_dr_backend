package com.doctor.dr.status.dto.response;

import jakarta.persistence.Column;

public class StatusResponseDTO {
    private final Long id;
    private final String name;
    private final Integer value;
    private final String description;
    private final String relatedTable;
    private final boolean isActive;

    public StatusResponseDTO(Long id, String name, Integer value, String description, String relatedTable, boolean isActive) {
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

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getRelatedTable() {
        return relatedTable;
    }

    public boolean isActive() {
        return isActive;
    }
}
