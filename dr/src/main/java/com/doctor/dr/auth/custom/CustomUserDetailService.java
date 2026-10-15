package com.doctor.dr.auth.custom;

import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.service.information.UserCredentialDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserCredentialDetailService userCredentialDetailService;

    public CustomUserDetailService(UserCredentialDetailService userCredentialDetailService) {
        this.userCredentialDetailService = userCredentialDetailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = this.userCredentialDetailService.loadUserCredentialByUserName(username);
        if (userCredential == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetail(userCredential);
    }
}
