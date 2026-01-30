package com.doctor.dr.submission.mapper;

import com.doctor.dr.submission.dto.request.UserProfileDetailRequestDTO;
import com.doctor.dr.submission.dto.response.UserProfileDetailResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "SubmissionUserProfileDetailMapperImpl")
public interface UserProfileDetailMapper {
    @Named("toUserProfile")
    UserProfile toUserProfile(UserProfileDetailRequestDTO userProfileDetailRequestDTO);

    @Named("toUserProfileDetailResponseDTO")
    UserProfileDetailResponseDTO toUserProfileDetailResponseDTO(UserProfile userProfile);
}
