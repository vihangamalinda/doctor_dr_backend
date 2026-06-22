package com.doctor.dr.user.profile.dto.request;

public class UpdateUserProfilePasswordDTO {
    private final Long userProfileId;
    private final String newPassword;

    public UpdateUserProfilePasswordDTO(Long userProfileId, String newPassword) {
        this.userProfileId = userProfileId;
        this.newPassword = newPassword;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
