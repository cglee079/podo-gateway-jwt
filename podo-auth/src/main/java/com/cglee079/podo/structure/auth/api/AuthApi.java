package com.cglee079.podo.structure.auth.api;

import com.cglee079.podo.structure.auth.global.security.TokenAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthApi {

    public static final String HEADER_STRING = "Authorization";

    @GetMapping("/join/{user}/{password}")
    public void join(@PathVariable("user") String user, @PathVariable("password") String password, HttpServletResponse response){

        final String token = TokenAuthenticationService.addAuthentication(user + " / " + password);
        response.setHeader(HEADER_STRING, token);
    }

    @GetMapping("/user")
    public void login(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
    }

}
