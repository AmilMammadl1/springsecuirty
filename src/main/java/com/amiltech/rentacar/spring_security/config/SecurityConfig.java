package com.amiltech.rentacar.spring_security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationService jwtAuthenticationService;

    @Bean
    @SuppressWarnings("all")
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .cors();
        http
        .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("/login").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers(HttpMethod.POST,"/a").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/a").permitAll()
                                .requestMatchers(HttpMethod.POST,"/b").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/b").permitAll()
                                .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationService, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
