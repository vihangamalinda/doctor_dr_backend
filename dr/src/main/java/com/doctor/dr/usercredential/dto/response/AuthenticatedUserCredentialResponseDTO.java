package com.doctor.dr.usercredential.dto.response;

import java.util.List;

import static java.lang.System.currentTimeMillis;

public class AuthenticatedUserCredentialResponseDTO {
    private final String accessToken;
    private final String tokenType;
    private final long expiresIn;
    private final String userName;
    private final List<String> userRoles;

    public AuthenticatedUserCredentialResponseDTO(String accessToken, String userName, List<String> userRoles) {
        this.accessToken = accessToken;
        this.expiresIn = configureExpiresIn();
        this.userName = userName;
        this.userRoles = userRoles;
        this.tokenType = "Bearer";
    }

    private long configureExpiresIn() {
        final int TEN_HOURS_IN_MILLISECONDS = 1000 * 60 * 60 * 10;
        return currentTimeMillis() + TEN_HOURS_IN_MILLISECONDS;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }
}
