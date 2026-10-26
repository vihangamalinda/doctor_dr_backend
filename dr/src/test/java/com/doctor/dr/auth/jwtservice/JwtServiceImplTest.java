package com.doctor.dr.auth.jwtservice;

import com.doctor.dr.auth.custom.CustomUserDetail;
import com.doctor.dr.usercredential.entity.UserCredential;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.MalformedInputException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {

    private  JwtServiceImpl jwtService;

    @BeforeEach
    void setUp(){
        this.jwtService=new JwtServiceImpl();
    }

    @Test
    void generateToken_shouldAllUserNameToBeExtracted(){
        String username="vihanga_malinda_test";

        String token=this.jwtService.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertEquals(username,this.jwtService.extractUsername(token));
    }

    @Test
    void isTokenValid_shouldReturnTrue_whenUsernameMatches(){
        String username="test_user_name";
        String token =this.jwtService.generateToken(username);
        UserCredential userCredential = mock(UserCredential.class);

        UserDetails customUserDetail= new CustomUserDetail(userCredential);
        when(customUserDetail.getUsername()).thenReturn("test_user_name");
        this.jwtService.isTokenValid(token,customUserDetail);
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenUsernameDoesNotMatch() {
        String username="not_matching_user_name";
        String token = this.jwtService.generateToken(username);

        UserCredential userCredential = mock(UserCredential.class);
        UserDetails customUSerDetails = new CustomUserDetail(userCredential);

        assertFalse(this.jwtService.isTokenValid(token,customUSerDetails));
    }

    @Test
    void extractUsername_shouldThrowException_whenTokenIsMalformed() {
        String malFormedToken ="not_a_valid_token";

        Exception exception=assertThrows(JwtException.class,()->this.jwtService.extractUsername(malFormedToken));
        assertInstanceOf(MalformedJwtException.class,exception);
    }
}