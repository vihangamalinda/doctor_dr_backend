package com.doctor.dr.userrole.dto.response;

import jakarta.persistence.Column;

public class UserRoleResponseDTO {
    private final Long id;
    private final String name;
    private final boolean isActive;
    private final String value;

    public UserRoleResponseDTO(Long id, String name, boolean isActive, String value) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getValue() {
        return value;
    }
}
