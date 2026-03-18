package com.doctor.dr.userrole.dto.request;

public class UserRoleRequestDTO {
    private final String name;
    private final String value;

    public UserRoleRequestDTO(String name,String value) {
        this.name = name;
        this.value=value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
