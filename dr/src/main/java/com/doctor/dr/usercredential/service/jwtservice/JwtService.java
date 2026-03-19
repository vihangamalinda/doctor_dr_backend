package com.doctor.dr.usercredential.service.jwtservice;

public interface JwtService {
    String generateToken(String username);
}
