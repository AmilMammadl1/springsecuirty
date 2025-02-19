package com.amiltech.rentacar.spring_security.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Verification {
    @Id
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private User user;

    private Boolean isUsed;

    private Boolean isExpired;

    private LocalDateTime expirationTime;}
