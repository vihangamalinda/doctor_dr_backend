package com.doctor.dr.auth.jwtservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private String secretKey = null;
    private final String TOKEN_ISSUER = "DOCTOR_DR";
    private final int TEN_HOURS_IN_MILLISECONDS = 1000 * 60 * 60 * 10;

    public JwtServiceImpl() {
        configureSecureKey();
    }

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
                .signWith(getKey())
                .compact();
    }

    @Override
    public String extractUsername(String jwtToken) {
        return null;
    }

    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userdetails) {
        return false;
    }

    private void configureSecureKey()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            this.secretKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Error generating key: " + e.getMessage());
        }
    }

    private Key getKey() {
        if (this.secretKey == null) {
            configureSecureKey();
        }

        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
