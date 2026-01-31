package com.doctor.dr.user.profile.dto.request;

public class HospitalDetailRequestDTO {
    private final Long id;

    public HospitalDetailRequestDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
