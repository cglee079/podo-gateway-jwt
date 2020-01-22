package com.cglee079.podo.structure.server.domain.test.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class TestApi {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @GetMapping("/**")
    public String test(HttpServletRequest request) {
        final String servletPath = request.getServletPath();
        final String token = getToken(request.getHeader(AUTHORIZATION_HEADER_KEY));
        final String jwtBody = getJwtBody(token);

        final String response = String.format("Path : '%s', JWT Value : '%s'", servletPath, jwtBody);

        log.info("Get Request : " + response);

        return response;
    }

    private String getToken(String header) {
        return header.replace(TOKEN_PREFIX, "").trim();
    }

    private String getJwtBody(String token) {
        int i = token.lastIndexOf('.');
        String withoutSignature = token.substring(0, i + 1);
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
        return untrusted.getBody().getSubject();
    }
}
