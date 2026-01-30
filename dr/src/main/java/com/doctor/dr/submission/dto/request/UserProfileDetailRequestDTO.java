package com.doctor.dr.submission.dto.request;

public class UserProfileDetailRequestDTO {
    private final Long id;

    public UserProfileDetailRequestDTO(Long id) {
        this.id =id;
    }

    public Long getId() {
        return id;
    }
}
