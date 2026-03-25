package com.doctor.dr.auth.cookieservice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieServiceImpl implements CookieService {
    private final int TEN_HOURS_IN_SECONDS = 60 * 60 * 10;


    @Override
    public Cookie getCookieWithToken(String jwtToken) {
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setValue(jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // for production environments, ensure this is set to true
        cookie.setPath("/");
        cookie.setMaxAge(TEN_HOURS_IN_SECONDS);
        return cookie;
    }

    @Override
    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;

    }

    @Override
    public void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // for production environments, ensure this is set to true
        cookie.setPath("/");
        cookie.setMaxAge(0); // This will delete the cookie
        response.addCookie(cookie);
    }
}
