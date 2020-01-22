package com.cglee079.podo.structure.auth.global.security.filter;

import com.cglee079.podo.structure.auth.domain.user.api.AuthApi;
import com.cglee079.podo.structure.auth.global.security.TokenAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String authHeader = httpServletRequest.getHeader(AuthApi.HEADER_STRING);

        final Authentication authentication = TokenAuthenticationService.authenticate(authHeader);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }

}
