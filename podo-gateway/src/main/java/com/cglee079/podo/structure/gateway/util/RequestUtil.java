package com.cglee079.podo.structure.gateway.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

public class RequestUtil {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    public static String getAuthToken(ServerHttpRequest request){
        final HttpHeaders headers = request.getHeaders();

        final List<String> authorizations = headers.get(HEADER_STRING);
        if (Objects.isNull(authorizations) || authorizations.isEmpty()) {
            return null;
        }

        final String authValue = authorizations.get(0);

        if(StringUtils.isEmpty(authValue)){
            return null;
        }

        return authValue.replace(TOKEN_PREFIX, "").trim();
    }
}
