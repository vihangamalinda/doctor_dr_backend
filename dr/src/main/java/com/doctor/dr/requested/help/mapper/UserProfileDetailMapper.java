package com.doctor.dr.requested.help.mapper;

import com.doctor.dr.requested.help.dto.request.UserProfileDetailRequestDTO;
import com.doctor.dr.requested.help.dto.response.UserProfileDetailResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "RequestedHelpUserProfileDetailMapperImpl")
public interface UserProfileDetailMapper {
    @Named("toUserProfile")
    UserProfile toUserProfile(UserProfileDetailRequestDTO userProfileDetailRequestDTO);

    @Named("toUserProfileDetailResponseDTO")
    UserProfileDetailResponseDTO toUserProfileDetailResponseDTO(UserProfile userProfile);


}
