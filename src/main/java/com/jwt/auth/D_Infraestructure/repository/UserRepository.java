package com.jwt.auth.D_Infraestructure.repository;

import com.jwt.auth.A_Domain.security.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByUsername(String username);
}
