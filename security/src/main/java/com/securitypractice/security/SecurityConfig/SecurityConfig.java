package com.securitypractice.security.SecurityConfig;

import static org.springframework.security.config.Customizer.withDefaults;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.securitypractice.security.SecurityConfig.Jwts.AuthProviderFilterToken;
import com.securitypractice.security.SecurityConfig.Jwts.JwtsUtils;
import com.securitypractice.security.Services.CustomizeUserDetailsService;
import com.securitypractice.security.Services.UserService;
import com.securitypractice.security.Services.ServiceImpl.UserImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    @Lazy
    private AuthProviderFilterToken authProviderFilterToken;

    @Bean
    SecurityFilterChain ssSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (request) -> request
                        .requestMatchers("/api/create").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers("/api/user").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/api/admin").hasAnyRole("ADMIN")
                        .anyRequest().authenticated());
        System.out.println("Request matching");
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // http.httpBasic((basic -> basic.disable()));
        http.httpBasic(withDefaults());
        http.csrf((csrf -> csrf.disable()));
        System.out.println("Adding Before");
        http.addFilterBefore(authProviderFilterToken, UsernamePasswordAuthenticationFilter.class);
        System.out.println("Everything Build");
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CustomizeUserDetailsService customizeUserDetailsService() {
        return new CustomizeUserDetailsService();
    }

    @Bean
    public AuthProviderFilterToken authProviderFilterToken() {
        return new AuthProviderFilterToken();
    }

}
