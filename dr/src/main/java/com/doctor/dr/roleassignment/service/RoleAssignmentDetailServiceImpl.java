package com.doctor.dr.roleassignment.service;

import com.doctor.dr.roleassignment.dto.request.RoleAssignmentRequestDTO;
import com.doctor.dr.roleassignment.entity.RoleAssignment;
import com.doctor.dr.roleassignment.repository.RoleAssignmentRepository;
import com.doctor.dr.userrole.entity.UserRole;
import com.doctor.dr.userrole.service.information.UserRoleDetailService;
import org.springframework.stereotype.Service;

@Service
public class RoleAssignmentDetailServiceImpl implements RoleAssignmentDetailService {
    private final RoleAssignmentRepository roleAssignmentRepository;
    private final UserRoleDetailService userRoleDetailService;

    public RoleAssignmentDetailServiceImpl(RoleAssignmentRepository roleAssignmentRepository, UserRoleDetailService userRoleDetailService) {
        this.roleAssignmentRepository = roleAssignmentRepository;
        this.userRoleDetailService = userRoleDetailService;
    }

    @Override
    public void create(RoleAssignmentRequestDTO dto) {
        UserRole userRole = this.userRoleDetailService.getUserRoleById(dto.getUserRoleId());

        RoleAssignment roleAssignment = new RoleAssignment();

        roleAssignment.setUserRole(userRole);
        roleAssignment.setUserCredential(dto.getUserCredential());
        roleAssignment.setIsActive(true);

        persistEntity(roleAssignment);
    }

    private void persistEntity(RoleAssignment roleAssignment) {
        this.roleAssignmentRepository.save(roleAssignment);
    }
}
