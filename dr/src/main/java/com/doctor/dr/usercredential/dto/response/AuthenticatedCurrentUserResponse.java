package com.doctor.dr.usercredential.dto.response;

import lombok.Builder;

import java.util.List;

public class AuthenticatedCurrentUserResponse {
    private  String userName;
    private  List<String> authorities;
    private  boolean isAccountNonExpired;
    private final boolean isAuthenticated;
    private  Long userProfileId;

    public AuthenticatedCurrentUserResponse( boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public AuthenticatedCurrentUserResponse(String userName, List<String> authorities, boolean isAccountNonExpired, boolean isAuthenticated, Long userProfileId) {
        this.userName = userName;
        this.authorities = authorities;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAuthenticated = isAuthenticated;
        this.userProfileId = userProfileId;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }
}
