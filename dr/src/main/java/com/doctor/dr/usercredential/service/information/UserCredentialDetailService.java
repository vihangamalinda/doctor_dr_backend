package com.doctor.dr.usercredential.service.information;

import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;

public interface UserCredentialDetailService {
    UserCredential loadUserCredentialByUserName(String username);

    void createUserCredentials(UserCredentialRegistrationRequestDTO dto);
}
