package com.cglee079.podo.structure.auth.global.security.filter;

import com.cglee079.podo.structure.auth.api.AuthApi;
import com.cglee079.podo.structure.auth.global.security.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request1 = (HttpServletRequest) request;
        Authentication authentication = TokenAuthenticationService.getAuthentication(request1.getHeader(AuthApi.HEADER_STRING));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }

}
