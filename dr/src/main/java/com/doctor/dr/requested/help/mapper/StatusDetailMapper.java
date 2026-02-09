package com.doctor.dr.requested.help.mapper;

import com.doctor.dr.requested.help.dto.response.StatusDetailResponseDTO;
import com.doctor.dr.status.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "RequestedHelpStatusDetailMapperImpl")
public interface StatusDetailMapper {
    @Named("toStatusDetailResponseDTO")
    StatusDetailResponseDTO toStatusDetailResponseDTO(Status status);
}
