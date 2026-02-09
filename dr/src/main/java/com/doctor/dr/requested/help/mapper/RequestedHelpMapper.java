package com.doctor.dr.requested.help.mapper;

import com.doctor.dr.requested.help.dto.request.RequestedHelpRequestDTO;
import com.doctor.dr.requested.help.dto.response.RequestedHelpResponseDTO;
import com.doctor.dr.requested.help.entity.RequestedHelp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StatusDetailMapper.class, UserProfileDetailMapper.class})
public interface RequestedHelpMapper {
    @Mapping(source = "createdByUserProfile", target = "createdByUserProfile", qualifiedByName = "toUserProfile")
    RequestedHelp toRequestedHelp(RequestedHelpRequestDTO requestedHelpRequestDTO);

    @Mapping(source = "createdByUserProfile", target = "createdByUserProfile", qualifiedByName = "toUserProfileDetailResponseDTO")
    @Mapping(source = "reviewedByUserProfile", target = "reviewedByUserProfile", qualifiedByName = "toUserProfileDetailResponseDTO")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusDetailResponseDTO")
    RequestedHelpResponseDTO toRequestedHelpResponseDTO(RequestedHelp requestedHelp);
}
