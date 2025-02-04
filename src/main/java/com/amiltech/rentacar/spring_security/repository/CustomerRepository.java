package com.amiltech.rentacar.spring_security.repository;

import com.amiltech.rentacar.spring_security.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String username);

}
