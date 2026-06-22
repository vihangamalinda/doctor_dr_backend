package com.doctor.dr.usercredential.service;

import com.doctor.dr.auth.cookieservice.CookieService;
import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.AuthenticatedCurrentUserResponse;
import com.doctor.dr.usercredential.dto.response.AuthenticatedUserCredentialResponseDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.auth.jwtservice.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CookieService cookieService;

    public UserCredentialServiceImpl(UserCredentialRepository userCredentialRepository, AuthenticationManager authenticationManager, JwtService jwtService, CookieService cookieService) {
        this.userCredentialRepository = userCredentialRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
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
    public AuthenticatedUserCredentialResponseDTO login(UserLoginRequestDTO dto, HttpServletResponse httpServletResponse) {

        Authentication authenticationToken = getAuthenticationToken(dto);

        if(authenticationToken.isAuthenticated()) {
            String username = dto.getUsername();

            List<String> userRoleNames = getUserRoleNames(username);
            String token =jwtService.generateToken(username);
            Cookie cookie=cookieService.getCookieWithToken(token);
            httpServletResponse.addCookie(cookie);

            AuthenticatedUserCredentialResponseDTO authResponse=
                    new AuthenticatedUserCredentialResponseDTO(token, username,userRoleNames);
            return authResponse;
        }else {
            System.out.println("Authentication failed.");
            return null;
        }
    }

    @Override
    public AuthenticatedCurrentUserResponse getCurrentLoggedInUser(Authentication authentication) {
        if(authentication ==null){
            return new AuthenticatedCurrentUserResponse(false);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserCredential userCredential = this.userCredentialRepository
                .findUserProfileByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User profile not found for the given username."));

        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Long userProfileId = userCredential.getUserProfile().getId();

        return new AuthenticatedCurrentUserResponse(
                userDetails.getUsername(),
                authorities,
                userDetails.isAccountNonExpired(),
                authentication.isAuthenticated(),
                userProfileId);


    }

    @Override
    public void logout(HttpServletResponse httpServletResponse) {
        this.cookieService.clearTokenCookie(httpServletResponse);
    }

    private List<String> getUserRoleNames(String username) {
        UserCredential credential =findByUserName(username);
        return retrieveUserRoleNames(credential);
    }

    private Authentication getAuthenticationToken(UserLoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private  List<String> retrieveUserRoleNames(UserCredential credential) {
        return credential.getRoleAssignments().stream().map(roleAssignment -> roleAssignment.getUserRole().getName()).toList();
    }

    private UserCredential findByUserName(String userName) {
        return this.userCredentialRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("Given username is not registered."));
    }
}
