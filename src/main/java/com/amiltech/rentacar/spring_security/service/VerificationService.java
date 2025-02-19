package com.amiltech.rentacar.spring_security.service;

import com.amiltech.rentacar.spring_security.model.User;

public interface VerificationService {

    String sendVerificationEmail(User user);
    User getUserByValidToken(String token);
}
