package com.doctor.dr.usercredential.entity;

import com.doctor.dr.roleassignment.entity.RoleAssignment;
import com.doctor.dr.user.profile.entity.UserProfile;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User_Credentials_Table")
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToOne()
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userCredential")
    private Set<RoleAssignment> roleAssignments = new HashSet<>();

    public UserCredential() {
    }

    public UserCredential(Long id, String userName, String password, boolean isActive, UserProfile userProfile) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
        this.userProfile = userProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<RoleAssignment> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(Set<RoleAssignment> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }
}
