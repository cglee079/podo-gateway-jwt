package com.cglee079.podo.structure.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenValue {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expireIn;

    public TokenValue(String accessToken, String refreshToken, String tokenType, long expireIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.expireIn = expireIn;
    }
}
