package com.doctor.dr.status.dto.request;

import jakarta.persistence.Column;

public class StatusRequestDTO {
    private final String name;
    private final Integer value;
    private final String description;
    private final String relatedTable;

    public StatusRequestDTO(String name, Integer value, String description, String relatedTable) {
        this.name = name;
        this.value = value;
        this.description = description;
        this.relatedTable = relatedTable;
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
}
