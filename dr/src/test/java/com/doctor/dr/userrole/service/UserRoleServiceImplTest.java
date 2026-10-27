package com.doctor.dr.userrole.service;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.userrole.dto.request.UserRoleRequestDTO;
import com.doctor.dr.userrole.dto.response.UserRoleResponseDTO;
import com.doctor.dr.userrole.entity.UserRole;
import com.doctor.dr.userrole.mapper.UserRoleMapper;
import com.doctor.dr.userrole.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceImplTest {
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private UserRoleMapper userRoleMapper;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @Test
    void getAll_shouldReturnEmptyList_whenNoUserRoleExist() {
        when(this.userRoleRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<UserRoleResponseDTO> result = this.userRoleService.getAll();

        assertEquals(0, result.size());
        verify(this.userRoleRepository, times(1)).findAllByIsActiveTrue();
        verify(this.userRoleMapper, never()).toUserRoleResponseDTO(any(UserRole.class));
    }

    @Test
    void getAll_shouldReturnUserRoles_whenUserRoleExist() {
        UserRole userRole = mock(UserRole.class);
        UserRoleResponseDTO userRoleResponseDTO = mock(UserRoleResponseDTO.class);

        when(this.userRoleRepository.findAllByIsActiveTrue()).thenReturn(List.of(userRole));
        when(this.userRoleMapper.toUserRoleResponseDTO(any())).thenReturn(userRoleResponseDTO);

        List<UserRoleResponseDTO> result = this.userRoleService.getAll();

        assertEquals(1, result.size());
        verify(this.userRoleRepository, times(1)).findAllByIsActiveTrue();
        verify(this.userRoleMapper, times(1)).toUserRoleResponseDTO(any(UserRole.class));

    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenNameIsNull() {
        UserRoleRequestDTO requestDTO = mock(UserRoleRequestDTO.class);
        when(requestDTO.getName()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.userRoleService.create(requestDTO));

        assertEquals("Provided information are not valid.", exception.getMessage());
        verify(this.userRoleRepository, never()).save(any(UserRole.class));
        verify(this.userRoleMapper, never()).toUserRole(any(UserRoleRequestDTO.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenNameIsBlank() {
        UserRoleRequestDTO requestDTO = mock(UserRoleRequestDTO.class);
        when(requestDTO.getName()).thenReturn("        ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.userRoleService.create(requestDTO));

        assertEquals("Provided information are not valid.", exception.getMessage());
        verify(this.userRoleRepository, never()).save(any(UserRole.class));
        verify(this.userRoleMapper, never()).toUserRole(any(UserRoleRequestDTO.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenValueIsNull() {
        UserRoleRequestDTO requestDTO = mock(UserRoleRequestDTO.class);
        when(requestDTO.getName()).thenReturn("admin");
        when(requestDTO.getValue()).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.userRoleService.create(requestDTO));

        assertEquals("Provided information are not valid.", exception.getMessage());
        verify(this.userRoleRepository, never()).save(any(UserRole.class));
        verify(this.userRoleMapper, never()).toUserRole(any(UserRoleRequestDTO.class));
    }

    @Test
    void create_shouldThrowIllegalArgumentException_whenValueIsBlank() {
        UserRoleRequestDTO requestDTO = mock(UserRoleRequestDTO.class);
        when(requestDTO.getName()).thenReturn("admin");
        when(requestDTO.getValue()).thenReturn("      ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.userRoleService.create(requestDTO));

        assertEquals("Provided information are not valid.", exception.getMessage());
        verify(this.userRoleRepository, never()).save(any(UserRole.class));
        verify(this.userRoleMapper, never()).toUserRole(any(UserRoleRequestDTO.class));
    }

    @Test
    void create_shouldCreateUserRole_whenValidInformationIsGiven() {
        UserRoleRequestDTO requestDTO = mock(UserRoleRequestDTO.class);
        when(requestDTO.getName()).thenReturn("admin");
        when(requestDTO.getValue()).thenReturn("ADMIN_TYPE");

        UserRole userRole = mock(UserRole.class);

        when(this.userRoleMapper.toUserRole(requestDTO)).thenReturn(userRole);
        when(this.userRoleRepository.save(any())).thenReturn(userRole);
        this.userRoleService.create(requestDTO);

        verify(this.userRoleRepository, times(1)).save(any(UserRole.class));
        verify(this.userRoleMapper, times(1)).toUserRole(any(UserRoleRequestDTO.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.userRoleService.getById(4L));

        verify(this.userRoleRepository, times(1)).findById(anyLong());
        verify(this.userRoleMapper, never()).toUserRoleResponseDTO(any(UserRole.class));
    }

    @Test
    void getById_shouldReturnUserRole_whenValidIdIsGiven() {
        UserRole userRole = mock(UserRole.class);
        UserRoleResponseDTO responseDTO = mock(UserRoleResponseDTO.class);

        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.of(userRole));
        when(this.userRoleMapper.toUserRoleResponseDTO(any())).thenReturn(responseDTO);

        UserRoleResponseDTO result = this.userRoleService.getById(5L);
        assertNotNull(result);
        verify(this.userRoleRepository, times(1)).findById(anyLong());
        verify(this.userRoleMapper, times(1)).toUserRoleResponseDTO(any(UserRole.class));
    }

    @Test
    void deleteById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven() {
        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.userRoleService.deleteById(4L));

        verify(this.userRoleRepository, times(1)).findById(anyLong());
        verify(this.userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void deleteById_shouldIgnore_whenDeactivatedUserRoleIdIsGiven() {
        UserRole userRole = mock(UserRole.class);
        when(userRole.getIsActive()).thenReturn(false);

        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.of(userRole));

        this.userRoleService.deleteById(5L);

        verify(this.userRoleRepository, times(1)).findById(anyLong());
        verify(this.userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void deleteById_shouldDeactivatedUserRole_whenValidUserRoleIdIsGiven() {
        UserRole userRole = mock(UserRole.class);
        when(userRole.getIsActive()).thenReturn(true);

        UserRoleResponseDTO responseDTO = mock(UserRoleResponseDTO.class);

        when(this.userRoleRepository.findById(anyLong())).thenReturn(Optional.of(userRole));

        this.userRoleService.deleteById(5L);

        verify(this.userRoleRepository, times(1)).findById(anyLong());
        verify(this.userRoleRepository, times(1)).save(any(UserRole.class));
    }

    @Test
    void getUserRoleByUserRoleValue_shouldReturnThrowResourceNotFoundException_whenInvalidUserRoleValueIsGiven(){
        String value= "NOT_VALID_VALUE";
        when(this.userRoleRepository.findByValue(anyString())).thenReturn(Optional.empty());

        Exception exception=assertThrows(ResourceNotFoundException.class,()->this.userRoleService.getUserRoleByUserRoleValue(value));

        assertEquals("Given User role value was not found. "+value,exception.getMessage());
        verify(this.userRoleRepository,times(1)).findByValue(anyString());
        verify(this.userRoleMapper,never()).toUserRoleResponseDTO(any(UserRole.class));
    }

    @Test
    void getUserRoleByUserRoleValue_shouldReturnUserRoleResponseDTO_whenValidUserRoleValueIsGiven(){
        String userRoleValue = "ADMIN_VALUE";
        UserRole userRole= mock(UserRole.class);
        UserRoleResponseDTO responseDTO = mock(UserRoleResponseDTO.class);
        when(this.userRoleRepository.findByValue(anyString())).thenReturn(Optional.of(userRole));
        when(this.userRoleMapper.toUserRoleResponseDTO(any())).thenReturn(responseDTO);

        UserRoleResponseDTO result =this.userRoleService.getUserRoleByUserRoleValue(userRoleValue);

        assertNotNull(result);
        verify(this.userRoleRepository,times(1)).findByValue(anyString());
        verify(this.userRoleMapper,times(1)).toUserRoleResponseDTO(any(UserRole.class));
    }

}