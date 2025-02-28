package com.amiltech.rentacar.spring_security.dto.response;

public record UserRegisterDTO(String email, String name, String surname, String token) {
}