package com.johnsmith.SpringBootInstagramApi.security;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.johnsmith.SpringBootInstagramApi.exceptions.ApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
	@Value("${application.security.jsonwebtoken.token.secret}")
    private String JWT_TOKEN_SECRET;

    @Value("${application.security.jsonwebtoken.token.expirationInMs}")
    private Long JWT_TOKEN_EXPIRATION;
    
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_TOKEN_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(this.getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (UnsupportedJwtException exception) {
            log.error("Unsupported jwt: {}", exception.getLocalizedMessage());
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Token not support.");
        } catch (SignatureException | MalformedJwtException exception) {
            log.error("Token is invalid format: {}", exception.getLocalizedMessage());
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Token is invalid format.");
        } catch (ExpiredJwtException exception) {
            log.error("Token is expired: {}", exception.getLocalizedMessage());
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Token is expired.");
        } catch (Exception exception) {
            log.error("{}", exception.getLocalizedMessage());
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Unknown error.");
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return this.extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return this.extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !this.isTokenExpired(token);
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION))
                .signWith(this.getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return this.generateToken(new HashMap<>(), userDetails);
    }
}
