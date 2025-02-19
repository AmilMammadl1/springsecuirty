package com.amiltech.rentacar.spring_security.repository;

import com.amiltech.rentacar.spring_security.model.Role;
import com.amiltech.rentacar.spring_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
