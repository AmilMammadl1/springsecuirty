package com.amiltech.rentacar.spring_security.repository;

import com.amiltech.rentacar.spring_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndStatusAndIsActiveIn(String username, Boolean status, List<Boolean> isActive);
}
