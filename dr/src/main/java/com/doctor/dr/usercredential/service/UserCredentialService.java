package com.doctor.dr.usercredential.service;

import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.AuthenticatedUserCredentialResponseDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserCredentialService {
    List<UserCredentialResponseDTO> getAll();

    AuthenticatedUserCredentialResponseDTO login(UserLoginRequestDTO userLoginRequestDTO, HttpServletResponse httpServletResponse);
}
