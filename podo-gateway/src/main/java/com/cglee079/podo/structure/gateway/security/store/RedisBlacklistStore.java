package com.cglee079.podo.structure.gateway.security.store;

import com.cglee079.podo.structure.gateway.security.BlacklistStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisBlacklistStore implements BlacklistStore {

    private static final String REDIS_KEY_PREFIX = "blacklist:token:access:";
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void add(String accessToken) {
        stringRedisTemplate.opsForValue().set(compileKey(accessToken), accessToken);
        stringRedisTemplate.expire(compileKey(accessToken), 100000, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean valid(String accessToken) {
        return !stringRedisTemplate.hasKey(compileKey(accessToken));
    }

    private String compileKey(String accessToken) {
        return REDIS_KEY_PREFIX + accessToken;
    }

}
