package com.cglee079.podo.structure.auth;

import com.cglee079.podo.structure.auth.global.infra.memberapi.MemberApiClient;
import com.cglee079.podo.structure.core.vo.JwtValue;
import com.cglee079.podo.structure.core.vo.MemberVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${jwt.expire_time}")
    private long expireTime = 10000000;

    @Value("${jwt.secret}")
    private String secret;

    private final PasswordEncoder passwordEncoder;
    private final MemberApiClient memberApiClient;

    public String authenticate(String memberId, String password) {
        final MemberVo member = memberApiClient.getMember(memberId);

        if (StringUtils.isEmpty(member)) {
            return null;
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            return null;
        }

        final JwtValue JWTValue = new JwtValue(member.getId(), member.getRoles(), LocalDateTime.now());

        return createToken(JWTValue);
    }

    private String createToken(JwtValue JWTValue) {

        final JacksonSerializer serializer = new JacksonSerializer(OBJECT_MAPPER);

        return Jwts.builder()
                .setSubject(toJson(JWTValue))
                .serializeToJsonWith(serializer)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    private String toJson(JwtValue JWTValue) {
        try {
            return OBJECT_MAPPER.writeValueAsString(JWTValue);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
