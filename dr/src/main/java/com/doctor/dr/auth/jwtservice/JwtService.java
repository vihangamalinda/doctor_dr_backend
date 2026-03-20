package com.doctor.dr.auth.jwtservice;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);

    String extractUsername(String jwtToken);

    boolean isTokenValid(String jwtToken, UserDetails userdetails);
}
