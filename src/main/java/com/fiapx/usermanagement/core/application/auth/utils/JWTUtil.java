package com.fiapx.usermanagement.core.application.auth.utils;

import com.fiapx.usermanagement.core.application.auth.config.AuthConfig;
import com.fiapx.usermanagement.core.application.auth.model.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtil {

    private AuthConfig authConfig;

    private static JWTUtil instance;

    private JWTUtil(AuthConfig authConfig){
        this.authConfig = authConfig;
    }

    public static JWTUtil getInstance(AuthConfig authConfig){
        if(instance == null){
            instance = new JWTUtil(authConfig);
        }
        return instance;
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + authConfig.getTokenExpiryTime()))
                .signWith(decodeSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(decodeSecretKey()).parseClaimsJws(token).getBody();
    }

    private SecretKey decodeSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode(authConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails){return true;}
}
