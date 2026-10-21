package com.doctor.dr.usercredential.service;

import com.doctor.dr.auth.cookieservice.CookieService;
import com.doctor.dr.auth.jwtservice.JwtService;
import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.roleassignment.entity.RoleAssignment;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.AuthenticatedCurrentUserResponse;
import com.doctor.dr.usercredential.dto.response.AuthenticatedUserCredentialResponseDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.userrole.entity.UserRole;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCredentialServiceImplTest {
    @Mock
    private UserCredentialRepository userCredentialRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private CookieService cookieService;
    @InjectMocks
    private UserCredentialServiceImpl userCredentialService;


    @Test
    void getAll_shouldReturnUserCredentials_whenUserCredentialExist(){
        UserCredential credential = new UserCredential();
        when(this.userCredentialRepository.findAll()).thenReturn(List.of(credential));

        List<UserCredentialResponseDTO> result = this.userCredentialService.getAll();

        assertEquals(1,result.size());
        verify(this.userCredentialRepository,times(1)).findAll();
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoUserCredentialExist(){
        when(this.userCredentialRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserCredentialResponseDTO> result = this.userCredentialService.getAll();

        assertEquals(0,result.size());
        verify(this.userCredentialRepository,times(1)).findAll();
    }

    @Test
    void logout_shouldPerformLogout_whenHttpServletResponseProvided(){

        HttpServletResponse response = mock(HttpServletResponse.class);
        doNothing().when(this.cookieService).clearTokenCookie(any());
        this.userCredentialService.logout(response);

        verify(this.cookieService,times(1)).clearTokenCookie(response);
    }

    @Test
    void getCurrentLoggedInUser_shouldReturnIsAuthenticatedAsFalse_whenAuthenticationIsNull(){
        AuthenticatedCurrentUserResponse authenticatedCurrentUserResponse=this.userCredentialService.getCurrentLoggedInUser(null);
        assertFalse(authenticatedCurrentUserResponse.isAuthenticated());
        verify(this.userCredentialRepository,times(0)).findUserProfileByUserName(any());
    }

    @Test
    void getCurrentLoggedInUser_shouldReturnIsAuthenticatedAsFalse_whenAuthenticationPrincipleIsNull(){
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(null);

        AuthenticatedCurrentUserResponse authenticatedCurrentUserResponse=this.userCredentialService.getCurrentLoggedInUser(authentication);
        assertFalse(authenticatedCurrentUserResponse.isAuthenticated());
        verify(this.userCredentialRepository,times(0)).findUserProfileByUserName(any());
    }

    @Test
    void getCurrentLoggedInUser_shouldReturnThrowResourceNotFoundException_whenInvalidUsernameIsGiven(){
        UserDetails userDetails = mock(UserDetails.class);
        String username = "no-existing-user-name";
        when(userDetails.getUsername()).thenReturn(username);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(this.userCredentialRepository.findUserProfileByUserName(anyString())).thenReturn(Optional.empty());

        Exception exception =assertThrows(ResourceNotFoundException.class,()-> this.userCredentialService.getCurrentLoggedInUser(authentication));
        assertEquals("User profile not found for the given username.",exception.getMessage());
        verify(this.userCredentialRepository,times(1)).findUserProfileByUserName(username);
    }



    @Test
    void getCurrentLoggedInUser_shouldReturnAuthenticatedCurrentUserResponse_whenValidAuthenticationIsGiven(){
        String username = "vihanga-malinda";

        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getId()).thenReturn(22L);

        UserCredential userCredential = mock(UserCredential.class);
        when(userCredential.getUserProfile()).thenReturn(userProfile);

        GrantedAuthority authority =mock(GrantedAuthority.class);
        when(authority.getAuthority()).thenReturn("Admin");
        List<GrantedAuthority> authorityList = List.of(authority);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDetails.isAccountNonExpired()).thenReturn(true);
        doReturn(authorityList).when(userDetails).getAuthorities();


        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.isAuthenticated()).thenReturn(true);

        when(this.userCredentialRepository.findUserProfileByUserName(username)).thenReturn(Optional.of(userCredential));

        AuthenticatedCurrentUserResponse result =this.userCredentialService.getCurrentLoggedInUser(authentication);
        assertEquals("vihanga-malinda",result.getUserName());
        assertEquals(1,result.getAuthorities().size());
        assertEquals("Admin",result.getAuthorities().get(0));

        assertTrue(result.isAccountNonExpired());
        assertEquals(22L,result.getUserProfileId());

    }

    @Test
    void login_shouldPropagateBadCredentialsException_whenAuthenticationFails(){

        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO("user-name","123");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        when(this.authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Incorrect credentials."));
        Exception exception=assertThrows(BadCredentialsException.class ,()->this.userCredentialService.login(requestDTO,httpServletResponse));

        assertEquals("Incorrect credentials.",exception.getMessage());
        verify(this.userCredentialRepository,times(0)).findByUserName(anyString());
        verify(jwtService, never()).generateToken(anyString());
        verify(httpServletResponse, never()).addCookie(any(Cookie.class));

    }


    @Test
    void login_shouldPropagateResourceNotFoundException_whenInvalidUserNameIsGiven(){

        String username = "user-name";
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO(username,"123");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        when(this.authenticationManager.authenticate(any())).thenReturn(authentication);
        when(this.userCredentialRepository.findByUserName(anyString())).thenReturn(Optional.empty());
        Exception exception=assertThrows(ResourceNotFoundException.class ,()->this.userCredentialService.login(requestDTO,httpServletResponse));

        assertEquals("Given username is not registered.",exception.getMessage());
        verify(this.userCredentialRepository,times(1)).findByUserName(username);
        verify(jwtService, never()).generateToken(anyString());
        verify(httpServletResponse, never()).addCookie(any(Cookie.class));
    }



    @Test
    void login_shouldReturnAuthenticatedResponse_whenValidCredentialIsGiven(){
        String username = "vihanga_malinda";
        UserLoginRequestDTO requestDTO =new UserLoginRequestDTO(username,"asdw123");
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);

        UserRole adminUserRole = mock(UserRole.class);
        when(adminUserRole.getName()).thenReturn("ADMIN_ROLE");

        RoleAssignment roleAssignment = mock(RoleAssignment.class);
        when(roleAssignment.getUserRole()).thenReturn(adminUserRole);
        Set<RoleAssignment> roleAssignmentSet = Set.of(roleAssignment);

        UserCredential userCredential = mock(UserCredential.class);
        when(userCredential.getRoleAssignments()).thenReturn(roleAssignmentSet);

        Cookie cookie = mock(Cookie.class);
        String tokenStr = "token-jwt-test";

        when(this.userCredentialRepository.findByUserName(anyString())).thenReturn(Optional.of(userCredential));
        when(this.authenticationManager.authenticate(any())).thenReturn(authentication);

        when(this.jwtService.generateToken(anyString())).thenReturn(tokenStr);
        when(this.cookieService.getCookieWithToken(anyString())).thenReturn(cookie);

        AuthenticatedUserCredentialResponseDTO result=this.userCredentialService.login(requestDTO,httpServletResponse);

        assertEquals(username,result.getUserName());
        assertEquals(tokenStr,result.getAccessToken());
        assertEquals(1,result.getUserRoles().size());
        assertEquals("ADMIN_ROLE",result.getUserRoles().get(0));

        verify(this.userCredentialRepository,times(1)).findByUserName(username);
        verify(this.authenticationManager,times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(this.jwtService,times(1)).generateToken(username);
        verify(this.cookieService,times(1)).getCookieWithToken(tokenStr);
    }


}