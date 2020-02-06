package com.cglee079.podo.structure.gateway.security;

import com.cglee079.podo.structure.gateway.security.store.RedisBlacklistStore;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SecurityStoreConfig {

    @Bean
    public BlacklistStore blacklistStore(StringRedisTemplate stringRedisTemplate){
        return new RedisBlacklistStore(stringRedisTemplate);
    }

}
