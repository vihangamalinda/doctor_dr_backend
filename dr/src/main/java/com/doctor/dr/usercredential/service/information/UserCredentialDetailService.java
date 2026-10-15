package com.doctor.dr.usercredential.service.information;

import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import jakarta.servlet.http.HttpServletResponse;

public interface UserCredentialDetailService {
    UserCredential loadUserCredentialByUserName(String username);

    void createUserCredentials(UserCredentialRegistrationRequestDTO dto);

    void updatePassword(UserCredential userCredential,String currentPassword, String newPassword, HttpServletResponse response);

}
