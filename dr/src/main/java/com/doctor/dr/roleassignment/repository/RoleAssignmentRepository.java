package com.doctor.dr.roleassignment.repository;

import com.doctor.dr.roleassignment.entity.RoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAssignmentRepository extends JpaRepository<RoleAssignment, Long> {
}
