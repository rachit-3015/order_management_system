package com.mobiuso.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class JwtTokenProvider {



    SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY .getBytes());

    public String generateJwtToken(Authentication authentication){
        String jwt = Jwts.builder().setIssuer("Rachit Patel")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email", authentication.getName())
                .signWith(key)
                .compact();

        return jwt;
    }

    public String getEmailFromToken(String token) {

        log.info("before claims ----------- ");

        token=token.substring(7);

        Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        String email= String.valueOf(claims.get("email"));

        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritieSet=new HashSet<>();
        for(GrantedAuthority authority:collection) {
            authoritieSet.add(authority.getAuthority());
        }
        return String.join(",", authoritieSet);
    }
}
