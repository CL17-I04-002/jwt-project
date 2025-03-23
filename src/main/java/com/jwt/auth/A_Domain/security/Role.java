package com.jwt.auth.A_Domain.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<GrantedPermission> permissions;
}
