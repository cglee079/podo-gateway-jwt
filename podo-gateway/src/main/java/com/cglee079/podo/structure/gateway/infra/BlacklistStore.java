package com.cglee079.podo.structure.gateway.infra;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// Black List 관리 스토어
// Redis?
@Component
public class BlacklistStore {

    private final Set<String> store = new HashSet<>();

    public void add(String accessToken){
        this.store.add(accessToken);
    }

    public boolean valid(String accessToken){
        return !this.store.contains(accessToken);
    }

}
