package com.amiltech.rentacar.spring_security.controller;

import com.amiltech.rentacar.spring_security.dto.request.LoginRequestDTO;
import com.amiltech.rentacar.spring_security.dto.request.UserRequestDTO;
import com.amiltech.rentacar.spring_security.dto.response.TokenResponseDTO;
import com.amiltech.rentacar.spring_security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRequestDTO dto) {
        authenticationService.createUser(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(dto));
    }
    @GetMapping("/a")
    public ResponseEntity<String> aNoAuth() {
        return ResponseEntity.status(HttpStatus.OK).body("a");
    }
    @PostMapping("/a")
    public ResponseEntity<String> aAuth() {
        return ResponseEntity.status(HttpStatus.OK).body("a");
    }
    @GetMapping("/b")
    public ResponseEntity<String> bNoAuth() {
        return ResponseEntity.status(HttpStatus.OK).body("b");
    }
    @PostMapping("/b")
    public ResponseEntity<String> bAuth() {
        return ResponseEntity.status(HttpStatus.OK).body("b");
    }
}
