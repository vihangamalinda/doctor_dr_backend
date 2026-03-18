package com.doctor.dr.roleassignment.entity;

import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.userrole.entity.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "Role_Assignment_Table")
public class RoleAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_credential_id",nullable = false)
    private UserCredential userCredential;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id",nullable = false)
    private UserRole userRole;

    @Column(name = "is_active")
    private boolean isActive;

    public RoleAssignment() {
    }

    public RoleAssignment(Long id, UserCredential userCredential, UserRole userRole,boolean isActive) {
        this.id = id;
        this.userCredential = userCredential;
        this.userRole = userRole;
        this.isActive =isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
