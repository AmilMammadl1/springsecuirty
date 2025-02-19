package com.amiltech.rentacar.spring_security.service.impl;

import com.amiltech.rentacar.spring_security.model.User;
import com.amiltech.rentacar.spring_security.model.Verification;
import com.amiltech.rentacar.spring_security.repository.VerificationRepository;
import com.amiltech.rentacar.spring_security.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final VerificationRepository verificationRepository;
    private Long EXPIRATION_TIME = 3600L;
    @Override
    public String sendVerificationEmail(User user) {
        Verification verification = new Verification();
        verification.setUser(user);
        verification.setIsUsed(false);
        verification.setToken(generateVerificationToken());
        verification.setExpirationTime(LocalDateTime.now().plusSeconds(EXPIRATION_TIME));
        verification.setIsExpired(false);
        verificationRepository.save(verification);
        return verification.getToken();
    }

    @Override
    public User getUserByValidToken(String token) {
        Verification verification = verificationRepository.findByToken(token);
        if(verification == null) return null;
        verification.setIsUsed(true);
        verificationRepository.save(verification);
        return verification.getUser();
    }

    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }
}
