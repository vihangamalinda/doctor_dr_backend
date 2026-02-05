package com.doctor.dr.reported.issue.dto.request;

public class UserProfileDetailRequestDTO {
    private final Long id;

    public UserProfileDetailRequestDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
