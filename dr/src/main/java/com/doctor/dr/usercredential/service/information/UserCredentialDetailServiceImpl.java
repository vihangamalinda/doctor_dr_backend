package com.doctor.dr.usercredential.service.information;

import com.doctor.dr.auth.cookieservice.CookieService;
import com.doctor.dr.roleassignment.dto.request.RoleAssignmentRequestDTO;
import com.doctor.dr.roleassignment.service.RoleAssignmentDetailService;
import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.usercredential.service.emailsender.EmailInformation;
import com.doctor.dr.usercredential.service.emailsender.EmailSenderService;
import com.doctor.dr.usercredential.service.passwordgenerator.SecurePasswordGenerator;
import com.doctor.dr.userrole.service.information.UserRoleDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCredentialDetailServiceImpl implements UserCredentialDetailService {
    private final UserCredentialRepository userCredentialRepository;
    private final RoleAssignmentDetailService roleAssignmentDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderService emailSenderService;
    private final CookieService cookieService;

    public UserCredentialDetailServiceImpl(UserCredentialRepository userCredentialRepository, RoleAssignmentDetailService roleAssignmentDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, EmailSenderService emailSenderService, CookieService cookieService) {
        this.userCredentialRepository = userCredentialRepository;
        this.roleAssignmentDetailService = roleAssignmentDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailSenderService = emailSenderService;
        this.cookieService = cookieService;
    }

    @Override
    public UserCredential loadUserCredentialByUserName(String username) {
        return findByUserName(username);
    }

    @Override
    public void createUserCredentials(UserCredentialRegistrationRequestDTO dto) {
        String password= createSecurePassword();

//        System.out.println("**************");
//        System.out.println("Generated password:->" + password);
//        System.out.println("**************");

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

    @Override
    public void updatePassword(UserCredential userCredential,String currentPassword, String newPassword, HttpServletResponse response) {
        if(!bCryptPasswordEncoder.matches(currentPassword, userCredential.getPassword())){
            throw new BadCredentialsException("The current password is not correct.");
        }

        String encryptedPassword = encryptPassword(newPassword);
        userCredential.setPassword(encryptedPassword);
        persistEntity(userCredential);

        EmailInformation emailInformation = new
                EmailInformation(userCredential.getUserProfile().getEmail(),userCredential.getUserName(),newPassword,false);
        this.emailSenderService.sendSimpleEmail(emailInformation);
        this.performLogout(response);
    }

    private  void performLogout(HttpServletResponse response){
        cookieService.clearTokenCookie(response);
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
