package com.doctor.dr.user.profile.dto.request;

public class UpdateUserProfilePasswordDTO {
    private final Long userProfileId;
    private final String currentPassword;
    private final String newPassword;

    public UpdateUserProfilePasswordDTO(Long userProfileId,
                                        String currentPassword,
                                        String newPassword) {
        this.userProfileId = userProfileId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }
}
