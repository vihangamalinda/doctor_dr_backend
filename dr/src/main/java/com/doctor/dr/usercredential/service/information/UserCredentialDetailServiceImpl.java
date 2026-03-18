package com.doctor.dr.usercredential.service.information;

import com.doctor.dr.roleassignment.dto.request.RoleAssignmentRequestDTO;
import com.doctor.dr.roleassignment.service.RoleAssignmentDetailService;
import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.usercredential.service.passwordgenerator.SecurePasswordGenerator;
import com.doctor.dr.userrole.service.information.UserRoleDetailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialDetailServiceImpl implements UserCredentialDetailService {
    private final UserCredentialRepository userCredentialRepository;
    private final RoleAssignmentDetailService roleAssignmentDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserCredentialDetailServiceImpl(UserCredentialRepository userCredentialRepository, RoleAssignmentDetailService roleAssignmentDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.roleAssignmentDetailService = roleAssignmentDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserCredential loadUserCredentialByUserName(String username) {
        return findByUserName(username);
    }

    @Override
    public void createUserCredentials(UserCredentialRegistrationRequestDTO dto) {
        String encryptedPassword = createEncryptedPassword();

        UserCredential userCredential = new UserCredential();
        userCredential.setUserName(dto.getUserName());
        userCredential.setPassword(encryptedPassword); //need to encrypt
        userCredential.setUserProfile(dto.getUserProfile());
        userCredential.setIsActive(true);

        userCredential = persistEntity(userCredential);

        RoleAssignmentRequestDTO roleAssignmentDTO = new RoleAssignmentRequestDTO(userCredential, dto.getUserRoleId());
        this.roleAssignmentDetailService.create(roleAssignmentDTO);
    }

    private String createEncryptedPassword() {
        String generate = SecurePasswordGenerator.generate(10);
        System.out.println("Password:->" + generate);

        String encode = bCryptPasswordEncoder.encode(generate);
        System.out.println("Encrypted password:->" + encode);


        return encode;//need to encrypt
    }

    private UserCredential persistEntity(UserCredential userCredential) {
        return this.userCredentialRepository.save(userCredential);
    }

    private UserCredential findByUserName(String username) {
        return this.userCredentialRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("The Given UserName is not found."));
    }
}
