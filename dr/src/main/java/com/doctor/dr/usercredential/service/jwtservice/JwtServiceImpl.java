package com.doctor.dr.usercredential.service.jwtservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private String secretKey = null;
    private final String TOKEN_ISSUER = "DOCTOR_DR";
    private final int TEN_HOURS_IN_MILLISECONDS = 1000 * 60 * 60 * 10;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuer(TOKEN_ISSUER)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TEN_HOURS_IN_MILLISECONDS))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        SecretKey key = Keys.hmacShaKeyFor(decode);
        return key;
    }

    public String getSecretKey() {
        this.secretKey = "gGGcDI8qi5jrjC1EWK+w23dqGuzLlMfWsdr6xbK2MRI="; //need to add this to environment variable or generate
        return this.secretKey;
    }
}
