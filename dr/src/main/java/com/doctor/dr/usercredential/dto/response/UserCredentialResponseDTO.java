package com.doctor.dr.usercredential.dto.response;

public class UserCredentialResponseDTO {
    private final String userName;

    public UserCredentialResponseDTO(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
