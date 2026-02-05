package com.doctor.dr.reported.issue.dto.response;

public class StatusDetailResponseDTO {
    private final Long id;
    private final String name;

    public StatusDetailResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
