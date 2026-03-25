package com.doctor.dr.auth.cookieservice;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    Cookie getCookieWithToken(String jwtToken);

    String getTokenFromCookie(HttpServletRequest request);

    void clearTokenCookie(HttpServletResponse response);
}
