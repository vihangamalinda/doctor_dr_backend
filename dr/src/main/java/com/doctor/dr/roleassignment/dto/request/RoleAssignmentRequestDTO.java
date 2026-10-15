package com.doctor.dr.roleassignment.dto.request;

import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.userrole.entity.UserRole;

public class RoleAssignmentRequestDTO {
    private final UserCredential userCredential;
    private final Long userRoleId;

    public RoleAssignmentRequestDTO(UserCredential userCredential, Long userRoleId) {
        this.userCredential = userCredential;
        this.userRoleId = userRoleId;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }
}
