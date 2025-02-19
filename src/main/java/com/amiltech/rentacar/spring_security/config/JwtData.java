package com.amiltech.rentacar.spring_security.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Scope(value = "request",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JwtData {
    private String username;
    private Long userId;
    private String role;
    private String token;
    private String tokenWithPrefix;
}
