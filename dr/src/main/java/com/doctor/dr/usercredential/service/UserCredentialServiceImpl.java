package com.doctor.dr.usercredential.service;

import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.userrole.service.information.UserRoleDetailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;

    public UserCredentialServiceImpl(UserCredentialRepository userCredentialRepository, AuthenticationManager authenticationManager) {
        this.userCredentialRepository = userCredentialRepository;
        this.authenticationManager = authenticationManager;
    }

    private UserCredential persistEntity(UserCredential userCredential) {
        return this.userCredentialRepository.save(userCredential);
    }

    @Override
    public List<UserCredentialResponseDTO> getAll() {
        List<UserCredential> userCredentials = this.userCredentialRepository.findAll();
        return userCredentials.stream().map(userCredential -> new UserCredentialResponseDTO(userCredential.getUserName())).collect(Collectors.toList());
    }

    @Override
    public String login(UserLoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authenticationToken= authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authenticationToken.isAuthenticated()) {
            System.out.println("User authenticated successfully.");
            return "77777777777777777777";
        }else {
            System.out.println("Authentication failed.");
            return "failed";
        }
    }

    private UserCredential findByUserName(String userName) {
        return this.userCredentialRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("Given username is not registered."));
    }
}
