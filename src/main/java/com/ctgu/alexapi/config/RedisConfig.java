package com.ctgu.alexapi.config;

import com.ctgu.alexapi.entity.UsersEntity;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setPassword(redisProperties.getPassword());
        config.setDatabase(0);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, UsersEntity> usersRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, UsersEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Key 用字符串序列化
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value 用 Jackson2JsonRedisSerializer
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(UsersEntity.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(UsersEntity.class));

        template.afterPropertiesSet();
        return template;
    }
}