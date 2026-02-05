package com.doctor.dr.reported.issue.mapper;

import com.doctor.dr.reported.issue.dto.response.StatusDetailResponseDTO;
import com.doctor.dr.status.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "ReportedIssueStatusDetailMapperImpl")
public interface StatusDetailMapper {
    @Named("toStatusDetailResponseDTO")
    StatusDetailResponseDTO toStatusDetailResponseDTO(Status status);
}
