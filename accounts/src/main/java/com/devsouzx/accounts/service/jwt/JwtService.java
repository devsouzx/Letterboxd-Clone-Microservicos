package com.devsouzx.accounts.service.jwt;

import com.devsouzx.accounts.database.model.User;
import com.devsouzx.accounts.dto.user.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    public TokenResponse generateToken(Authentication authentication, Boolean expire) {
        Map<String, Object> claims = new HashMap<>();

        final User user = (User) authentication.getPrincipal();

        final String auth = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(defineExpiration(expire))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TokenResponse.builder()
                .token(auth)
                .expiresIn(defineExpiration(expire).getTime())
                .firstAccess(user.getFirstAccess())
                .username(user.getUsername())
                .build();
    }

    private Date defineExpiration(Boolean expire) {
        if (expire) {
            return new Date(System.currentTimeMillis() + expiration);
        }
        return new Date(System.currentTimeMillis() + 31557600000000L);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}