package com.doctor.dr.reported.issue.mapper;

import com.doctor.dr.reported.issue.dto.request.UserProfileDetailRequestDTO;
import com.doctor.dr.reported.issue.dto.response.UserProfileDetailResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "ReportedIssueUserProfileDetailMapperImpl")
public interface UserProfileDetailMapper {
    @Named("toUserProfile")
    UserProfile toUserProfile(UserProfileDetailRequestDTO userProfileDetailRequestDTO);

    @Named("toUserProfileDetailResponseDTO")
    UserProfileDetailResponseDTO toUserProfileDetailResponseDTO(UserProfile UserProfile);

}
