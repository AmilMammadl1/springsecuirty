package com.amiltech.rentacar.spring_security.repository;

import com.amiltech.rentacar.spring_security.model.User;
import com.amiltech.rentacar.spring_security.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    @Query("""
                SELECT v FROM Verification v
                WHERE v.token = :token
                AND v.isUsed = false
                AND v.isExpired = false
                AND v.expirationTime > CURRENT_TIMESTAMP
           """)
    Verification getValidVerification(String token);

    Verification findByToken(String token);
}
