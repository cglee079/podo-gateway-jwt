package com.cglee079.podo.structure.auth.store;

import com.cglee079.podo.structure.auth.TokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class RedisTokenStore implements TokenStore {

    private final static String REDIS_KEY_PREFIX = "token:refresh:";
    private final Long refreshTokenExpireTime;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void save(String refreshToken, String accessToken) {
        stringRedisTemplate.opsForValue().set(compileKey(refreshToken), accessToken);
        stringRedisTemplate.expire(compileKey(refreshToken), refreshTokenExpireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean valid(String refreshToken) {
        return stringRedisTemplate.hasKey(compileKey(refreshToken));
    }

    @Override
    public void removeByRefreshToken(String refreshToken) {
        stringRedisTemplate.delete(compileKey(refreshToken));
    }

    @Override
    public void removeByAccessToken(String accessToken) {
        //TODO 어케하징
    }

    private String compileKey(String key){
        return REDIS_KEY_PREFIX + key;
    }


}
