package com.jwt.auth.B_Use_Cases.Interfaces;

import com.jwt.auth.A_Domain.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
