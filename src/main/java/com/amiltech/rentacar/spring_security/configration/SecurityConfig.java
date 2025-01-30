package com.amiltech.rentacar.spring_security.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
//            authorizationManagerRequestMatcherRegistry.requestMatchers("/a").permitAll();
//            authorizationManagerRequestMatcherRegistry.requestMatchers("/b").authenticated();
//        });

        http.authorizeHttpRequests()
                .requestMatchers("/a").permitAll() // Permit all requests to /a regardless of method
                .requestMatchers(HttpMethod.GET, "/b").authenticated() // Require authentication for GET requests to /b
                .requestMatchers(HttpMethod.POST, "/b").authenticated() // Require authentication for POST requests to /b
                .requestMatchers(HttpMethod.PUT, "/b").authenticated() // Require authentication for PUT requests to /b
                .anyRequest().authenticated(); // Require authentication for any other requests

        http.csrf().disable();
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

}
