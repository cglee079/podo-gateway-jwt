package com.cglee079.podo.structure.auth.global.security;

import com.cglee079.podo.structure.core.vo.TokenValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Slf4j
@Component
public class TokenAuthenticationService {

    @Value("${jwt.expire_time}")
    private long expireTime = 10000000;

    @Value("${jwt.secret}")
    private String secret;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String createToken(TokenValue tokenValue) {


        objectMapper = new ObjectMapper();
        final JacksonSerializer serializer = new JacksonSerializer(objectMapper);

        return Jwts.builder()
                .setSubject(toJson(tokenValue))
                .serializeToJsonWith(serializer)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    private String toJson(TokenValue tokenValue) {
        try {
            return objectMapper.writeValueAsString(tokenValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
