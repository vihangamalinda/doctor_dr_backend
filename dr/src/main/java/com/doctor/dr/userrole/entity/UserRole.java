package com.doctor.dr.userrole.entity;

import com.doctor.dr.roleassignment.entity.RoleAssignment;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User_Role_Table")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_role_name")
    private String name;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "user_role_value")
    private String value;

    @OneToMany()
    private Set<RoleAssignment> roleAssignments = new HashSet<>();

    public UserRole() {
    }

    public UserRole(Long id, String name, boolean isActive, String value) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleAssignment> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(Set<RoleAssignment> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
