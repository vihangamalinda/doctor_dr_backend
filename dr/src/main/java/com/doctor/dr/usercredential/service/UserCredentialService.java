package com.doctor.dr.usercredential.service;

import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;

import java.util.List;

public interface UserCredentialService {
    List<UserCredentialResponseDTO> getAll();

    void login(UserLoginRequestDTO userLoginRequestDTO);
}
