package com.doctor.dr.usercredential.dto.request;

import com.doctor.dr.user.profile.entity.UserProfile;


public class UserCredentialRegistrationRequestDTO {
    private final String userName;
    private final UserProfile userProfile;
    private final Long userRoleId;

    public UserCredentialRegistrationRequestDTO(String userName, UserProfile userProfile, Long userRoleId) {
        this.userName = userName;
        this.userProfile = userProfile;
        this.userRoleId = userRoleId;
    }

    public String getUserName() {
        return userName;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }
}
