package com.doctor.dr.user.profile.dto.request;

public class UserInternalSystemInformationRequestDTO {
    private final String userName;
    private final Long userRoleId;

    public UserInternalSystemInformationRequestDTO(String userName, Long userRoleId) {
        this.userName = userName;
        this.userRoleId = userRoleId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }
}
