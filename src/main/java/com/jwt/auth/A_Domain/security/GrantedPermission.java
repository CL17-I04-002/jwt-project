package com.jwt.auth.A_Domain.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "granted_permission")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrantedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "operation_id")
    private Operation operation;
}