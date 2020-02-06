package com.cglee079.podo.structure.auth.store;

import com.cglee079.podo.structure.auth.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTokenStore implements TokenStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    @Override
    public void save(String refreshToken, String accessToken){
        this.store.put(refreshToken, accessToken);
    }

    @Override
    public boolean valid(String refreshToken){
        return this.store.containsValue(refreshToken);
    }

    @Override
    public void removeByRefreshToken(String refreshToken) {
        store.remove(refreshToken);
    }

    @Override
    public void removeByAccessToken(String accessToken) {
        final Optional<String> first = store.keySet().stream().filter(key -> store.get(key).equals(accessToken)).findFirst();
        store.remove(first.orElse(null));
    }
}
