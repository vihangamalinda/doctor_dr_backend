package com.doctor.dr.userrole.service.information;

import com.doctor.dr.userrole.entity.UserRole;
import com.doctor.dr.userrole.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleDetailServiceImpl implements UserRoleDetailService{
    private final UserRoleRepository userRoleRepository;

    public UserRoleDetailServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getUserRoleById(long id) {
        return this.findById(id);
    }
    private UserRole findById(long id) {
        return this.userRoleRepository.findById(id).orElseThrow(() -> new RuntimeException("Given user role id was not found."));
    }
}
