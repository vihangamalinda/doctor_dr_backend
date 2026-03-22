package com.doctor.dr.usercredential.service.information;

import com.doctor.dr.roleassignment.dto.request.RoleAssignmentRequestDTO;
import com.doctor.dr.roleassignment.service.RoleAssignmentDetailService;
import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.usercredential.service.emailsender.EmailInformation;
import com.doctor.dr.usercredential.service.emailsender.EmailSenderService;
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
    private final EmailSenderService emailSenderService;

    public UserCredentialDetailServiceImpl(UserCredentialRepository userCredentialRepository, RoleAssignmentDetailService roleAssignmentDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailSenderService emailSenderService) {
        this.userCredentialRepository = userCredentialRepository;
        this.roleAssignmentDetailService = roleAssignmentDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public UserCredential loadUserCredentialByUserName(String username) {
        return findByUserName(username);
    }

    @Override
    public void createUserCredentials(UserCredentialRegistrationRequestDTO dto) {
        String password= createSecurePassword();
        String encryptedPassword = encryptPassword(password);

        UserCredential userCredential = new UserCredential();
        userCredential.setUserName(dto.getUserName());
        userCredential.setPassword(encryptedPassword); //need to encrypt
        userCredential.setUserProfile(dto.getUserProfile());
        userCredential.setIsActive(true);

        userCredential = persistEntity(userCredential);

        RoleAssignmentRequestDTO roleAssignmentDTO = new RoleAssignmentRequestDTO(userCredential, dto.getUserRoleId());
        this.roleAssignmentDetailService.create(roleAssignmentDTO);

        EmailInformation emailInformation = new
                EmailInformation(dto.getUserProfile().getEmail(),dto.getUserName(),password,true);
        this.emailSenderService.sendSimpleEmail(emailInformation);
    }

    private String createSecurePassword(){
        String generate = SecurePasswordGenerator.generate(10);
        System.out.println("Password:->" + generate);

        return generate;
    }
    private String encryptPassword(String password){
        String encode = bCryptPasswordEncoder.encode(password);
        System.out.println("Encrypted password:->" + encode);

        return encode;
    }

    private UserCredential persistEntity(UserCredential userCredential) {
        return this.userCredentialRepository.save(userCredential);
    }

    private UserCredential findByUserName(String username) {
        return this.userCredentialRepository.findByUserName(username).orElseThrow(() -> new RuntimeException("The Given UserName is not found."));
    }
}
