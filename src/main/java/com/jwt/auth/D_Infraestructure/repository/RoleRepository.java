package com.jwt.auth.D_Infraestructure.repository;

import com.jwt.auth.A_Domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String defaultRole);
}
