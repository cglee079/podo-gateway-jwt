package com.cglee079.podo.structure.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthApi {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    private final AuthService authService;

    @PostMapping("/login/{id}/{password}")
    public void login(@PathVariable("id") String memberId, @PathVariable("password") String password, HttpServletResponse response) {

        final String token = authService.authenticate(memberId, password);

        if(!StringUtils.isEmpty(token)){
            response.setHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
        }
    }

}
