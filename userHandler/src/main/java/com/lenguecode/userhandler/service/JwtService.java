package com.lenguecode.booknetworkapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration ;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey ;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()  // ✅ Updated method for JJWT 0.12.6
                .verifyWith((SecretKey) getSignInKey())  // ✅ `verifyWith()` replaces `setSigningKey()`
                .build()  // ✅ New requirement in JJWT 0.12.6
                .parseSignedClaims(token)  // ✅ Use `parseSignedClaims()` instead of `parseClaimsJws()`
                .getPayload();  // ✅ Extract claims correctly
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long jwtExpiration) {
        String authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        claims.put("roles", authorities);

        return  Jwts.builder()
                .claims(claims)  // ✅ `setClaims()` → `claims()` in JJWT 0.12.6
                .subject(userDetails.getUsername())  // ✅ `setSubject()` → `subject()`
                .issuedAt(new Date(System.currentTimeMillis()))  // ✅ `setIssuedAt()` → `issuedAt()`
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))  // ✅ `setExpiration()` → `expiration()`
                .signWith(getSignInKey())  // ✅ New `signWith()` method requires an algorithm
                .compact();
    }


    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
