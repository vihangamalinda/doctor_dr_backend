package com.doctor.dr.usercredential.service;

import com.doctor.dr.auth.cookieservice.CookieService;
import com.doctor.dr.usercredential.dto.request.UserLoginRequestDTO;
import com.doctor.dr.usercredential.dto.response.AuthenticatedUserCredentialResponseDTO;
import com.doctor.dr.usercredential.dto.response.UserCredentialResponseDTO;
import com.doctor.dr.usercredential.entity.UserCredential;
import com.doctor.dr.usercredential.repository.UserCredentialRepository;
import com.doctor.dr.auth.jwtservice.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
