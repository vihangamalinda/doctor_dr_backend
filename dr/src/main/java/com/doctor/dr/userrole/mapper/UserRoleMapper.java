package com.doctor.dr.userrole.mapper;

import com.doctor.dr.userrole.dto.request.UserRoleRequestDTO;
import com.doctor.dr.userrole.dto.response.UserRoleResponseDTO;
import com.doctor.dr.userrole.entity.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    UserRole toUserRole(UserRoleRequestDTO userRoleRequestDTO);

    UserRoleResponseDTO toUserRoleResponseDTO(UserRole userRole);
}
