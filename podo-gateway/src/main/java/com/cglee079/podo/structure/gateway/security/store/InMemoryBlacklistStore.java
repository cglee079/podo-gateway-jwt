package com.cglee079.podo.structure.gateway.security.store;

import com.cglee079.podo.structure.gateway.security.BlacklistStore;

import java.util.HashSet;
import java.util.Set;

public class InMemoryBlacklistStore implements BlacklistStore {

    private final Set<String> store = new HashSet<>();

    @Override
    public void add(String accessToken){
        this.store.add(accessToken);
    }

    @Override
    public boolean valid(String accessToken){
        return !this.store.contains(accessToken);
    }

}
