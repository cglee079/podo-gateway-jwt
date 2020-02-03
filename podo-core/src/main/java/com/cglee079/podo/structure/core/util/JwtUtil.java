package com.cglee079.podo.structure.core.util;

import com.cglee079.podo.structure.core.vo.JwtValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

public class JwtUtil {

    public static JwtValue getJwtValue(String token) {
        final int i = token.lastIndexOf('.');
        final String withoutSignature = token.substring(0, i + 1);
        final Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);

        final String subject = untrusted.getBody().getSubject();

        return parseJwtValue(subject);
    }

    private static JwtValue parseJwtValue(String subject) {
        try {
            return new ObjectMapper().readValue(subject, JwtValue.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
