package com.doctor.dr.status.mapper;

import com.doctor.dr.status.dto.request.StatusRequestDTO;
import com.doctor.dr.status.dto.response.StatusResponseDTO;
import com.doctor.dr.status.entity.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status toStatus(StatusRequestDTO statusRequestDTO);

    StatusResponseDTO toStatusResponseDTO(Status status);
}
