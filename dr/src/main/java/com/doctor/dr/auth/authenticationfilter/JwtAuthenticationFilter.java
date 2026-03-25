package com.doctor.dr.auth.authenticationfilter;

import com.doctor.dr.auth.cookieservice.CookieService;
import com.doctor.dr.auth.jwtservice.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CookieService cookieService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, CookieService cookieService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.cookieService = cookieService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        boolean isTokenInHeader = authHeader != null && authHeader.startsWith("Bearer ");
        String jwtToken =
                (isTokenInHeader) ? authHeader.substring(7) : this.cookieService.getTokenFromCookie(request);

//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            jwtToken = this.cookieService.getTokenFromCookie(request);
////            filterChain.doFilter(request, response);
//            return;
//        }else {
//            jwtToken = authHeader.substring(7);
//        }

        if(jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String username = jwtService.extractUsername(jwtToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (username != null && authentication == null) {
            // Perform authentication
            UserDetails userdetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwtToken, userdetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        }
        filterChain.doFilter(request, response);
    }
}
