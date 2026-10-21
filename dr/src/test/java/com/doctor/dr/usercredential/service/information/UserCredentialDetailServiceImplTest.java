package com.doctor.dr.usercredential.service.information;

import com.doctor.dr.auth.cookieservice.CookieService;
import com.doctor.dr.exception.PasswordUpdateInformationException;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.roleassignment.dto.request.RoleAssignmentRequestDTO;
import com.doctor.dr.roleassignment.service.RoleAssignmentDetailService;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.usercredential.service.emailsender.EmailInformation;
import com.doctor.dr.usercredential.service.emailsender.EmailSenderService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCredentialDetailServiceImplTest {
    @Mock
    private UserCredentialRepository userCredentialRepository;
    @Mock
    private RoleAssignmentDetailService roleAssignmentDetailService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private EmailSenderService emailSenderService;
    @Mock
    private CookieService cookieService;
    @InjectMocks
    private UserCredentialDetailServiceImpl userCredentialDetailService;

    @Test
    void loadUserCredentialByUserName_shouldThrowResourceNotFoundException_whenInvalidUserNameIsGiven(){
        String username = "user-name-test";
        when(this.userCredentialRepository.findByUserName(username)).thenReturn(Optional.empty());
        Exception exception =assertThrows(ResourceNotFoundException.class,()->this.userCredentialDetailService.loadUserCredentialByUserName(username));

        assertEquals("The Given UserName is not found.",exception.getMessage());
        verify(this.userCredentialRepository,times(1)).findByUserName(anyString());
    }

    @Test
    void loadUserCredentialByUserName_shouldReturnUserCredential_whenValidUserNameIsGiven(){
        String username = "user-name-test";
        UserCredential userCredential = mock(UserCredential.class);
        when(this.userCredentialRepository.findByUserName(username)).thenReturn(Optional.of(userCredential));
        UserCredential result= this.userCredentialDetailService.loadUserCredentialByUserName(username);
        assertNotNull(result);
        verify(this.userCredentialRepository,times(1)).findByUserName(anyString());
    }

    @Test
    void updatePassword_shouldThrowBadCredentialsException_whenInvalidCurrentPasswordIsGiven(){
        UserCredential userCredential = mock(UserCredential.class);
        when(userCredential.getPassword()).thenReturn("hjiklo");
        String currentPassword ="1234";
        String newPassword="asbd_00934-12jk";
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(this.bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(false);
        Exception exception =assertThrows(BadCredentialsException.class,()->this.userCredentialDetailService.updatePassword(userCredential,currentPassword,newPassword,httpServletResponse));

        assertEquals("The current password is not correct.",exception.getMessage());
        verify(this.bCryptPasswordEncoder,times(1)).matches(anyString(),anyString());
        verify(this.bCryptPasswordEncoder,never()).encode(anyString());
        verify(this.userCredentialRepository,never()).save(any(UserCredential.class));
        verify(this.emailSenderService,never()).sendSimpleEmail(any(EmailInformation.class));
        verify(this.cookieService,never()).clearTokenCookie(any(HttpServletResponse.class));
    }

    @Test
    void updatePassword_shouldThrowPasswordUpdateInformationException_whenInvalidNewPasswordIsGiven(){
        UserCredential userCredential = mock(UserCredential.class);
        when(userCredential.getPassword()).thenReturn("xyz_123");

        String currentPassword ="xyz_123";
        String newPassword ="123456789";

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        when(this.bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(true);

        Exception exception=assertThrows(PasswordUpdateInformationException.class,()->this.userCredentialDetailService.updatePassword(userCredential,currentPassword,newPassword,httpServletResponse));
        assertInstanceOf(IllegalArgumentException.class,exception);
        assertEquals("New password should be longer than 10 characters.",exception.getMessage());
        verify(this.bCryptPasswordEncoder,times(1)).matches(anyString(),anyString());
        verify(this.bCryptPasswordEncoder,never()).encode(anyString());
        verify(this.userCredentialRepository,never()).save(any(UserCredential.class));
        verify(this.emailSenderService,never()).sendSimpleEmail(any(EmailInformation.class));
        verify(this.cookieService,never()).clearTokenCookie(any(HttpServletResponse.class));
    }

    @Test
    void updatePassword_shouldPerformLogoutAfterSuccessfulPasswordUpdate_whenValidInformationIsGiven(){
        UserProfile userProfile = mock(UserProfile.class);

        UserCredential userCredential = mock(UserCredential.class);
        when(userCredential.getUserProfile()).thenReturn(userProfile);
        when(userCredential.getPassword()).thenReturn("xyz_123");

        String currentPassword ="xyz_123";
        String newPassword ="rty_1234_001jka";

        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        when(this.bCryptPasswordEncoder.matches(anyString(),anyString())).thenReturn(true);
        when(this.bCryptPasswordEncoder.encode(anyString())).thenReturn("ionjdhx");
        when(this.userCredentialRepository.save(any(UserCredential.class))).thenReturn(userCredential);
        doNothing().when(this.emailSenderService).sendSimpleEmail(any(EmailInformation.class));

        this.userCredentialDetailService.updatePassword(userCredential,currentPassword,newPassword,httpServletResponse);
        verify(this.bCryptPasswordEncoder,times(1)).matches(anyString(),anyString());
        verify(this.bCryptPasswordEncoder,times(1)).encode(anyString());
        verify(this.userCredentialRepository,times(1)).save(any(UserCredential.class));
        verify(this.emailSenderService,times(1)).sendSimpleEmail(any(EmailInformation.class));
        verify(this.cookieService,times(1)).clearTokenCookie(any(HttpServletResponse.class));
    }

    @Test
    void createUserCredentials_shouldThrowIllegalArgumentException_whenUserNameIsNull(){
        UserCredentialRegistrationRequestDTO requestDTO = mock(UserCredentialRegistrationRequestDTO.class);
        when(requestDTO.getUserName()).thenReturn(null);

        Exception exception =assertThrows(IllegalArgumentException.class,()->this.userCredentialDetailService.createUserCredentials(requestDTO));
        assertEquals("Requested data should be valid inorder to proceed.",exception.getMessage());
        verify(this.bCryptPasswordEncoder,never()).encode(anyString());
        verify(this.userCredentialRepository,never()).save(any(UserCredential.class));
        verify(this.roleAssignmentDetailService,never()).create(any(RoleAssignmentRequestDTO.class));
        verify(this.emailSenderService,never()).sendSimpleEmail(any(EmailInformation.class));
    }

    @Test
    void createUserCredentials_shouldThrowIllegalArgumentException_whenUserNameIsBlank(){
        UserCredentialRegistrationRequestDTO requestDTO = mock(UserCredentialRegistrationRequestDTO.class);
        when(requestDTO.getUserName()).thenReturn("      ");

        Exception exception =assertThrows(IllegalArgumentException.class,()->this.userCredentialDetailService.createUserCredentials(requestDTO));

        assertEquals("Requested data should be valid inorder to proceed.",exception.getMessage());
        verify(this.bCryptPasswordEncoder,never()).encode(anyString());
        verify(this.userCredentialRepository,never()).save(any(UserCredential.class));
        verify(this.roleAssignmentDetailService,never()).create(any(RoleAssignmentRequestDTO.class));
        verify(this.emailSenderService,never()).sendSimpleEmail(any(EmailInformation.class));
    }

    @Test
    void createUserCredentials_shouldThrowIllegalArgumentException_whenUserProfileIsNull(){
        UserCredentialRegistrationRequestDTO requestDTO = mock(UserCredentialRegistrationRequestDTO.class);
        when(requestDTO.getUserName()).thenReturn("user_name_test");
        when(requestDTO.getUserProfile()).thenReturn(null);

        Exception exception =assertThrows(IllegalArgumentException.class,()->this.userCredentialDetailService.createUserCredentials(requestDTO));

        assertEquals("Requested data should be valid inorder to proceed.",exception.getMessage());
        verify(this.bCryptPasswordEncoder,never()).encode(anyString());
        verify(this.userCredentialRepository,never()).save(any(UserCredential.class));
        verify(this.roleAssignmentDetailService,never()).create(any(RoleAssignmentRequestDTO.class));
        verify(this.emailSenderService,never()).sendSimpleEmail(any(EmailInformation.class));
    }

    @Test
    void createUserCredentials_shouldThrowIllegalArgumentException_whenUserRoleIsNull(){
        UserProfile userProfile = mock(UserProfile.class);

        UserCredentialRegistrationRequestDTO requestDTO = mock(UserCredentialRegistrationRequestDTO.class);
        when(requestDTO.getUserName()).thenReturn("user_name_test");
        when(requestDTO.getUserProfile()).thenReturn(userProfile);
        when(requestDTO.getUserRoleId()).thenReturn(null);

        Exception exception =assertThrows(IllegalArgumentException.class,()->this.userCredentialDetailService.createUserCredentials(requestDTO));

        assertEquals("Requested data should be valid inorder to proceed.",exception.getMessage());
        verify(this.bCryptPasswordEncoder,never()).encode(anyString());
        verify(this.userCredentialRepository,never()).save(any(UserCredential.class));
        verify(this.roleAssignmentDetailService,never()).create(any(RoleAssignmentRequestDTO.class));
        verify(this.emailSenderService,never()).sendSimpleEmail(any(EmailInformation.class));
    }

    @Test
    void createUserCredentials_shouldCreateUserCredential_whenValidDataIsGiven(){
        UserProfile userProfile = mock(UserProfile.class);

        UserCredentialRegistrationRequestDTO requestDTO = mock(UserCredentialRegistrationRequestDTO.class);
        when(requestDTO.getUserName()).thenReturn("user_name_test");
        when(requestDTO.getUserProfile()).thenReturn(userProfile);
        when(requestDTO.getUserRoleId()).thenReturn(1L);

        this.userCredentialDetailService.createUserCredentials(requestDTO);


        verify(this.bCryptPasswordEncoder,times(1)).encode(any(String.class));
        verify(this.userCredentialRepository,times(1)).save(any(UserCredential.class));
        verify(this.roleAssignmentDetailService,times(1)).create(any(RoleAssignmentRequestDTO.class));
        verify(this.emailSenderService,times(1)).sendSimpleEmail(any(EmailInformation.class));
    }
}