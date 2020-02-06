package com.cglee079.podo.structure.auth;

public interface TokenStore {
    void save(String refreshToken, String accessToken);

    boolean valid(String refreshToken);

    void removeByAccessToken(String accessToken);

    void removeByRefreshToken(String refreshToken);
}
