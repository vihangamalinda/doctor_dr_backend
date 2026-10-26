package com.doctor.dr.status.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StatusRequestDTO {
    @NotBlank(message = "Name is required.")
    private final String name;
    @NotNull(message = "Value is required.")
    private final Integer value;
    @NotBlank(message = "Description is required.")
    private final String description;
    @NotBlank(message = "Related table is required.")
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
