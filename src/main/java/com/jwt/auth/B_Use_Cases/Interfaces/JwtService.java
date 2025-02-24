package com.jwt.auth.B_Use_Cases.Interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails users, Map<String, Object> extraClaims);
}
