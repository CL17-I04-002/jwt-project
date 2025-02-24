package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.B_Use_Cases.Interfaces.JwtService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImp implements JwtService {

    private final Long expirationMinutes;
    private final String SECRET_KEY;

    public JwtServiceImp(@Value("${security.jwt.expiration-in-minutes}") Long expirationMinutes,
                         @Value("${security.jwt.secret-key}") String SECRET_KEY) {
        this.expirationMinutes = expirationMinutes;
        this.SECRET_KEY = SECRET_KEY;
    }

    /**
     * Generates jwt using extra claims and essential information
     * @param users
     * @param extraClaims
     * @return
     */
    @Override
    public String generateToken(UserDetails users, Map<String, Object> extraClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((expirationMinutes * 60 * 1000) + issuedAt.getTime());
        Map<String, Object> mapHeader = new HashMap<>();
        mapHeader.put(Header.TYPE, Header.JWT_TYPE);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(users.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setHeader(mapHeader)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();


    }

    private Key generateKey() {
        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(key);
    }
}
