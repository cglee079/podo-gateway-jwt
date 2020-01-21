package com.cglee079.podo.structure.auth.global.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationService {
    static final long EXPIRATION_TIME = 864_000_000; // 10 days

    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";

    public static String addAuthentication(String username) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return TOKEN_PREFIX + " " + JWT;

    }

    public static Authentication getAuthentication(String token) {
        if (token != null) {

            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))) :
                    null;
        }

        return null;
    }
}
