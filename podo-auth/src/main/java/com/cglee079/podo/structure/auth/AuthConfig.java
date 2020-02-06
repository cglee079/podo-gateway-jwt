package com.cglee079.podo.structure.auth;

import com.cglee079.podo.structure.auth.store.RedisTokenStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AuthConfig {

    @Bean
    public TokenStore tokenStore(@Value("${jwt.refresh_token.expire}") Long refreshTokenExpireTime, StringRedisTemplate redisTemplate){
        return new RedisTokenStore(refreshTokenExpireTime, redisTemplate);
    }
}
