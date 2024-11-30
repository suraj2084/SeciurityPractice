package com.securitypractice.security.SecurityConfig.Jwts;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.securitypractice.security.Services.CustomizeUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthProviderFilterToken extends OncePerRequestFilter {

    @Autowired
    private JwtsUtils jwtsUtils;
    @Autowired
    private CustomizeUserDetailsService customizeUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = jwtsUtils.getTokenFromHeader(request);
        if (jwt == null) {
            System.out.println("Authorization header is missing.");
        } else {
            System.out.println("Authorization header found: " + jwt);
        }
        try {

            if (jwt != null && jwtsUtils.validateToken(jwt)) {
                String userName = jwtsUtils.getUserNameFromToken(jwt);
                System.out.println("UserName=" + userName);
                UserDetails user = customizeUserDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                System.out.println("ROLE=" + user.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("ROLE=" + auth);

                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Security auth set");
            }
        } catch (Exception e) {
            System.out.println("Error processing JWT token: " + e);
        }
        filterChain.doFilter(request, response);
        System.out.println("FilterChain set");
    }

}
