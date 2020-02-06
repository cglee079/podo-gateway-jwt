package com.cglee079.podo.structure.auth;

import com.cglee079.podo.structure.auth.global.infra.memberapi.MemberApiClient;
import com.cglee079.podo.structure.core.util.JwtUtil;
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
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final JacksonSerializer<Map<String, ?>> SERIALIZER = new JacksonSerializer<>(OBJECT_MAPPER);
    private static final String TOKEN_TYPE = "bearer";

    @Value("${jwt.access_token.expire}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh_token.expire}")
    private long refreshTokenExpireTime;

    @Value("${jwt.secret}")
    private String secret;

    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;
    private final MemberApiClient memberApiClient;

    public TokenValue authenticate(String memberId, String password) {
        final MemberVo member = memberApiClient.getMember(memberId);

        if (StringUtils.isEmpty(member)) {
            return null;
        }

        if (!passwordEncoder.matches(password, member.getPassword())) {
            return null;
        }

        final JwtValue jwtValue = new JwtValue(member.getRoles(), LocalDateTime.now());

        final String accessToken = createAccessToken(jwtValue);
        final String refreshToken = createRefreshToken(jwtValue);

        tokenStore.save(refreshToken, accessToken);


        return new TokenValue(accessToken, refreshToken, TOKEN_TYPE, accessTokenExpireTime);
    }

    public TokenValue refresh(RefreshTokenDto refreshTokenDto) {
        final String refreshToken = refreshTokenDto.getRefreshToken();

        if (!tokenStore.valid(refreshToken)) {
            return null;
        }

        final JwtValue jwtValue = JwtUtil.getJwtValue(refreshToken);

        final String newAccessToken = createAccessToken(jwtValue);
        final String newRefreshToken = createRefreshToken(jwtValue);

        tokenStore.save(newRefreshToken, newAccessToken);
        tokenStore.removeByRefreshToken(refreshToken);

        return new TokenValue(newAccessToken, newRefreshToken, TOKEN_TYPE, accessTokenExpireTime);
    }

    public void logout(String accessToken) {
        this.tokenStore.removeByAccessToken(accessToken);
    }

    private String createAccessToken(JwtValue jwtValue) {

        return Jwts.builder()
                .setSubject(toJson(jwtValue))
                .serializeToJsonWith(SERIALIZER)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireTime))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    private String createRefreshToken(JwtValue jwtValue) {

        return Jwts.builder()
                .setSubject(toJson(jwtValue))
                .serializeToJsonWith(SERIALIZER)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTime))
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
