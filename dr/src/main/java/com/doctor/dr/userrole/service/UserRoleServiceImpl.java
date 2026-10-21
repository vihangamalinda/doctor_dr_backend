package com.doctor.dr.userrole.service;

import com.doctor.dr.exception.ResourceNotFoundException;
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
        this.validate(userRoleRequestDTO);

        UserRole userRole = this.userRoleMapper.toUserRole(userRoleRequestDTO);
        userRole.setIsActive(true);
        persistEntity(userRole);
    }

    private void validate(UserRoleRequestDTO dto) {
        if(dto.getName() ==null || dto.getName().isBlank() || dto.getValue()==null || dto.getValue().isBlank()){
           throw  new IllegalArgumentException("Provided information are not valid.");
        }
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
        return this.userRoleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Given user role id was not found."));
    }

    @Override
    public void deleteById(long id) {
        UserRole userRole = findById(id);
        if (userRole.getIsActive()) {
            userRole.setIsActive(false);
            persistEntity(userRole);
        }
    }

    @Override
    public UserRoleResponseDTO getUserRoleByUserRoleValue(String userRoleValue) {
        UserRole userRole =this.userRoleRepository.findByValue(userRoleValue)
                .orElseThrow(()-> new ResourceNotFoundException("Given User role value was not found. "+userRoleValue));
        return this.userRoleMapper.toUserRoleResponseDTO(userRole);
    }
}
