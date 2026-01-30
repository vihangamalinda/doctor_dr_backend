package com.doctor.dr.user.profile.mapper;

import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {LocationDetailMapper.class,HospitalDetailMapper.class})
public interface UserProfileMapper {
    UserProfile toUserProfile(UserProfileRequestDTO userProfileRequestDTO);

    UserProfileResponseDTO toUserProfileResponseDTO(UserProfile userProfile);
}
