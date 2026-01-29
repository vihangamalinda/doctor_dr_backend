package com.doctor.dr.submission.mapper;

import com.doctor.dr.status.entity.Status;
import com.doctor.dr.submission.dto.response.StatusDetailResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "SubmissionStatusDetailMapperImpl")
public interface StatusDetailMapper {
    @Named("toStatusDetailResponseDTO")
    StatusDetailResponseDTO toStatusDetailResponseDTO(Status status);
}
