package com.doctor.dr.usercredential.service;

import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.userrole.service.information.UserRoleDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;

    public UserCredentialServiceImpl(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
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
    public void login(UserLoginRequestDTO userLoginRequestDTO) {
        this.userCredentialRepository.findByUserName(userLoginRequestDTO.getUsername());

    }

    private UserCredential findByUserName(String userName) {
        return this.userCredentialRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("Given username is not registered."));
    }
}
