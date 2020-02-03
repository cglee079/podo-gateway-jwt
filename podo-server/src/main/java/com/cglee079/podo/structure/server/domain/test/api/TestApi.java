package com.cglee079.podo.structure.server.domain.test.api;

import com.cglee079.podo.structure.core.util.JwtUtil;
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

        final String response = String.format("Path : '%s', JWT Value : '%s'", servletPath, JwtUtil.getJwtValue(token));

        log.info("Get Request : " + response);

        return response;
    }

    private String getToken(String authorizationHeader) {
        return authorizationHeader.replace(TOKEN_PREFIX, "").trim();
    }


}
