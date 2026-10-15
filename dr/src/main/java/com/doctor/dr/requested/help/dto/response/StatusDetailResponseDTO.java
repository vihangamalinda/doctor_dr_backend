package com.doctor.dr.requested.help.dto.response;

public class StatusDetailResponseDTO {
    private final Long id;
    private final String name;
    private final int value;

    public StatusDetailResponseDTO(Long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
