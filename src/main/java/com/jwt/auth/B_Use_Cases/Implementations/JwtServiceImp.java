package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.B_Use_Cases.Interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
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

        return Jwts.builder()
                //header
                .header()
                    .type("JWT")
                    .and()
                // payload
                .subject(users.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                // signature
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();


    }

    /**
     * Extracts user
     * @param jwt
     * @return String
     */
    @Override
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    /**
     * Extracts all claims from a given JWT (JSON Web Token).
     *
     * This method verifies the JWT's signature using the application's secret key
     * and then retrieves the claims (payload data) contained within the token.
     * If the JWT is invalid or expired, an exception will be thrown.
     *
     * @param jwt The JWT string to be parsed.
     * @return A {@link Claims} object containing all claims from the token.
     * @throws io.jsonwebtoken.ExpiredJwtException if the token has expired.
     * @throws io.jsonwebtoken.SignatureException if the signature is invalid.
     * @throws io.jsonwebtoken.MalformedJwtException if the JWT format is incorrect.
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }

    private SecretKey generateKey() {
        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(key);
    }
}
