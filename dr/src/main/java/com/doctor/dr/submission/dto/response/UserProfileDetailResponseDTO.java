package com.doctor.dr.submission.dto.response;

public class UserProfileDetailResponseDTO {
    private final Long id;

    public UserProfileDetailResponseDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
