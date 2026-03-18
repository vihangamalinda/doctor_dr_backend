package com.doctor.dr.userrole.service;

import com.doctor.dr.userrole.dto.request.UserRoleRequestDTO;
import com.doctor.dr.userrole.dto.response.UserRoleResponseDTO;

import java.util.List;

public interface UserRoleService {
    List<UserRoleResponseDTO> getAll();

    void create(UserRoleRequestDTO userRoleRequestDTO);

    UserRoleResponseDTO getById(long id);

    void deleteById(long id);
}
