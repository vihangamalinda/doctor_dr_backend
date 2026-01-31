package com.doctor.dr.user.profile.service;

import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;

import java.util.List;

public interface UserProfileService {
    List<UserProfileResponseDTO> getAll();

    void create(UserProfileRequestDTO userProfileRequestDTO);

    UserProfileResponseDTO getById(long id);

    void deleteById(long id);
}
