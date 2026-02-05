package com.doctor.dr.reported.issue.mapper;

import com.doctor.dr.reported.issue.dto.request.ReportedIssueRequestDTO;
import com.doctor.dr.reported.issue.dto.response.ReportedIssueResponseDTO;
import com.doctor.dr.reported.issue.entity.ReportedIssue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StatusDetailMapper.class, UserProfileDetailMapper.class})
public interface ReportedIssueMapper {
    @Mapping(source = "userProfile", target = "userProfile", qualifiedByName = "toUserProfile")
    ReportedIssue toReportedIssue(ReportedIssueRequestDTO reportedIssueRequestDTO);

    @Mapping(source = "userProfile", target = "userProfile", qualifiedByName = "toUserProfileDetailResponseDTO")
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusDetailResponseDTO")
    ReportedIssueResponseDTO toReportedIssueResponseDTO(ReportedIssue reportedIssue);
}
