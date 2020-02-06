package com.cglee079.podo.structure.gateway.security;

public interface BlacklistStore {
    void add(String accessToken);

    boolean valid(String accessToken);
}
