package com.cglee079.podo.structure.auth.global.security;

import com.cglee079.podo.structure.auth.domain.user.vo.TokenValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;

@Slf4j
public class TokenAuthenticationService {
    static final long EXPIRATION_TIME = 10000000;

    static final String SECRET_KEY = "SECRET_KEY";
    static final String TOKEN_PREFIX = "Bearer";

    public static String createToken(TokenValue tokenValue) {
        final String JWT = Jwts.builder()
                .setSubject(toJson(tokenValue))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return TOKEN_PREFIX + " " + JWT;
    }

    public static Authentication authenticate(String authHeaderValue) {
        log.info("사용자 인증을 시작합니다, {}", authHeaderValue);

        if (StringUtils.isEmpty(authHeaderValue)) {
            return null;

        }

        final String token = authHeaderValue.replace(TOKEN_PREFIX, "").trim();

        try {
            final String tokenValueJson = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();


            final TokenValue tokenValue = readJson(tokenValueJson);

            return new UsernamePasswordAuthenticationToken(tokenValue, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (ExpiredJwtException e) {
            log.error("JWT Error ",  e);
            return null;
        }
    }


    private static String toJson(TokenValue tokenValue) {
        try {
            return new ObjectMapper().writeValueAsString(tokenValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static TokenValue readJson(String tokenValueJson) {
        try {
            return new ObjectMapper().readValue(tokenValueJson, TokenValue.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
