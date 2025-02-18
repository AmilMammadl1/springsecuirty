package com.amiltech.rentacar.spring_security.service.impl;

import com.amiltech.rentacar.spring_security.config.JwtService;
import com.amiltech.rentacar.spring_security.dto.request.LoginRequestDTO;
import com.amiltech.rentacar.spring_security.dto.request.UserRequestDTO;
import com.amiltech.rentacar.spring_security.dto.response.TokenResponseDTO;
import com.amiltech.rentacar.spring_security.mapper.UserMapper;
import com.amiltech.rentacar.spring_security.model.Role;
import com.amiltech.rentacar.spring_security.model.User;
import com.amiltech.rentacar.spring_security.repository.RoleRepository;
import com.amiltech.rentacar.spring_security.repository.UserRepository;
import com.amiltech.rentacar.spring_security.service.AuthenticationService;
import com.amiltech.rentacar.spring_security.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final VerificationService verificationService;
    //    private final KafkaProducer kafkaProducer;
    private final JwtService jwtService;
//    private final HttpServletResponse response;
//    @Value("${kafka.topic.user-registration}")
//    private String USER_REGISTRATION_TOPIC;


    @Override
    public void createUser(UserRequestDTO user) {
        User userCheck = userRepository.findByEmailAndStatusAndIsActiveIn(user.email(), true, List.of(true, false));
        if (userCheck != null) {
            throw new RuntimeException("User already exists");
        }
        User userCreate = userMapper.map(user);
        userCreate.setPassword(passwordEncoder.encode(user.password()));
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = userCreate.getRoles();
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(roleUser);
        userCreate.setRoles(roles);
        userRepository.save(userCreate);
        verificationService.sendVerificationEmail(userCreate);

    }

    @Override
    public void verifyUser(String token) {
        User user = verificationService.getUserByValidToken(token);
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    public TokenResponseDTO login(LoginRequestDTO dto) {
        User user = getByUsername(dto.email());
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        String access = jwtService.generateToken(user.getEmail(), generateClaims(user));
        String refresh = jwtService.generateRefresh(user.getEmail(), generateRefreshClaims(user));
        return new TokenResponseDTO(access, refresh);
    }

    @Override
    public TokenResponseDTO refresh(String refreshToken) {
        String username = jwtService.getUsername(refreshToken);
        User user = getByUsername(username);
        String access = jwtService.generateToken(user.getEmail(), generateClaims(user));
        String refresh = jwtService.generateRefresh(user.getEmail(), generateRefreshClaims(user));
        return new TokenResponseDTO(access, refresh);
    }

    public User getByUsername(String email) {
        User user = userRepository.findByEmailAndStatusAndIsActiveIn(email, true, List.of(true));
        if (user == null) throw new RuntimeException("User not found");
        return user;
    }

    private Map<String, Object> generateClaims(User user) {
        String roles = user.getRoles().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Map.of("role", roles, "username", user.getEmail(), "userId", user.getId());
    }

    private Map<String, Object> generateRefreshClaims(User user) {
        return Map.of("username", user.getEmail(), "userId", user.getId());
    }
}
