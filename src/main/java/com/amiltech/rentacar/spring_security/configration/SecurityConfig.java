package com.amiltech.rentacar.spring_security.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

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
                .requestMatchers("/register").permitAll() // Permit all requests to /a regardless of method
                .requestMatchers(HttpMethod.GET, "/b").authenticated() // Require authentication for GET requests to /b
                .requestMatchers(HttpMethod.POST, "/b").authenticated() // Require authentication for POST requests to /b
                .requestMatchers(HttpMethod.PUT, "/b").authenticated() // Require authentication for PUT requests to /b
                .anyRequest().authenticated(); // Require authentication for any other requests

        http.csrf().disable();
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User
//                .withUsername("user")
//                .password("{noop}salamavara123")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User
//                .withUsername("admin")
//                .password("{bcrypt}$2a$16$SLfYyv2uRte9IYnmxENMk.3xrDFt9DsAXCDxCEYdu4pr9rGZX1uf6")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

}
