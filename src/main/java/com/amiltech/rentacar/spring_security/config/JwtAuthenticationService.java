package com.amiltech.rentacar.spring_security.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationService extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final JwtData jwtData;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            Claims claims = jwtService.parseToken(token);
            if (claims != null) {
                Authentication authentication = getAuthentication(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }

    private Authentication getAuthentication(Claims claims) {
        String role = claims.get("role", String.class);
        String username = claims.get("username", String.class);
        Long userId = claims.get("userId", Long.class);

        jwtData.setUserId(userId);
        jwtData.setUsername(username);
        jwtData.setRole(role);

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        return new UsernamePasswordAuthenticationToken(username, null,
                authorities);
    }
}
