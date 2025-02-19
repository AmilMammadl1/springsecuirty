package com.amiltech.rentacar.spring_security.service;

import com.amiltech.rentacar.spring_security.dto.request.LoginRequestDTO;
import com.amiltech.rentacar.spring_security.dto.request.UserRequestDTO;
import com.amiltech.rentacar.spring_security.dto.response.TokenResponseDTO;

public interface AuthenticationService {
    void createUser(UserRequestDTO user);
    void verifyUser(String token);
    TokenResponseDTO login(LoginRequestDTO dto);
    TokenResponseDTO refresh(String refreshToken);
}
