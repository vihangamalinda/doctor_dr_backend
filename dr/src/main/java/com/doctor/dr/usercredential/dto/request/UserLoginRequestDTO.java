package com.doctor.dr.usercredential.dto.request;

public class UserLoginRequestDTO {
    private final String username;
    private final String password;

    public UserLoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
