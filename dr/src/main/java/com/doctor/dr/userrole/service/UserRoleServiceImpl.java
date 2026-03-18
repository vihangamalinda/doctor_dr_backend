package com.doctor.dr.userrole.service;

import com.doctor.dr.userrole.dto.request.UserRoleRequestDTO;
import com.doctor.dr.userrole.dto.response.UserRoleResponseDTO;
import com.doctor.dr.userrole.entity.UserRole;
import com.doctor.dr.userrole.mapper.UserRoleMapper;
import com.doctor.dr.userrole.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final UserRoleMapper userRoleMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<UserRoleResponseDTO> getAll() {
        List<UserRole> userRoles = this.userRoleRepository.findAllByIsActiveTrue();

        return userRoles.stream().map(this.userRoleMapper::toUserRoleResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void create(UserRoleRequestDTO userRoleRequestDTO) {
        UserRole userRole = this.userRoleMapper.toUserRole(userRoleRequestDTO);
        userRole.setIsActive(true);
        persistEntity(userRole);
    }

    private void persistEntity(UserRole userRole) {
        this.userRoleRepository.save(userRole);
    }

    @Override
    public UserRoleResponseDTO getById(long id) {
        UserRole userRole = findById(id);

        return this.userRoleMapper.toUserRoleResponseDTO(userRole);
    }

    private UserRole findById(long id) {
        return this.userRoleRepository.findById(id).orElseThrow(() -> new RuntimeException("Given user role id was not found."));
    }

    @Override
    public void deleteById(long id) {
        UserRole userRole = findById(id);
        if (userRole.getIsActive()) {
            userRole.setIsActive(false);
            persistEntity(userRole);
        }
    }
}
