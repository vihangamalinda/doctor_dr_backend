package com.doctor.dr.auth.custom;

import com.doctor.dr.usercredential.entity.UserCredential;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetail implements UserDetails {
    private final UserCredential userCredential;

    public CustomUserDetail(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> userRoles = getUserRoleValues();
        // need to check if he can get multiple Roles
        return Collections.singleton(new SimpleGrantedAuthority(userRoles.get(0)));
    }

    private List<String> getUserRoleValues() {
        return this.userCredential
                .getRoleAssignments().stream()
                .map(roleAssignment -> roleAssignment.getUserRole().getValue()).toList();
    }

    @Override
    public @Nullable String getPassword() {
        return this.userCredential.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userCredential.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userCredential.getIsActive();
    }
}
