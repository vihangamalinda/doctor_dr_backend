package com.doctor.dr.roleassignment.service;

import com.doctor.dr.roleassignment.dto.request.RoleAssignmentRequestDTO;
import com.doctor.dr.roleassignment.entity.RoleAssignment;
import com.doctor.dr.roleassignment.repository.RoleAssignmentRepository;
import com.doctor.dr.userrole.entity.UserRole;
import com.doctor.dr.userrole.service.information.UserRoleDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleAssignmentDetailServiceImplTest {
    @Mock
    private UserRoleDetailService userRoleDetailService;
    @Mock
    private RoleAssignmentRepository roleAssignmentRepository;
    @InjectMocks
    private RoleAssignmentDetailServiceImpl roleAssignmentDetailService;

    @Test
    void create_shouldCreate_whenValidInformationIsGiven() {
        UserRole userRole = new UserRole(44L, "name", true, "User-role");
        when(this.userRoleDetailService.getUserRoleById(anyLong())).thenReturn(userRole);


        RoleAssignmentRequestDTO dto = mock(RoleAssignmentRequestDTO.class);
//        when(dto.getUserCredential()).thenReturn(null);
//        when(dto.getUserRoleId()).thenReturn(userRole.getId());

        RoleAssignment roleAssignment = mock(RoleAssignment.class);

        when(this.roleAssignmentRepository.save(any(RoleAssignment.class))).thenReturn(roleAssignment);
        this.roleAssignmentDetailService.create(dto);

        verify(this.userRoleDetailService, times(1)).getUserRoleById(any(Long.class));
        verify(this.roleAssignmentRepository, times(1)).save(any(RoleAssignment.class));
    }

}