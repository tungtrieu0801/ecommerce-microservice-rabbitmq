package com.example.userservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * {@link JwtUtils}This class is response for handling JWT(JSON Web Token) operations such as generating, validating, extracting
 * information from token in Spring Security-based authentication system.
 */

@Component
public class JwtUtils {

    @Value("${jwtSecret}")
    private String jwtSecret; //The secret key used to sign the JWT

    @Value("${jwtIssuer}")
    private String jwtIssuer;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs; //The expiration time of the JWT in milliseconds

    public String generateToken(Authentication authentication) {
        return createToken(authentication);
    }

    private String createToken(Authentication authentication) {
        System.out.println("123 " + jwtSecret);
        UserInfoDetail userPrincipal = (UserInfoDetail) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .setIssuer(jwtIssuer)
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(jwtSecret.getBytes()))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extracAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Claims is component in PayLoad
    private Claims extracAllClaims(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(jwtSecret.getBytes())).parseClaimsJws(token).getBody();
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extracAllClaims(token).getExpiration().before(new Date());
    }

    public Boolean validateToken(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
