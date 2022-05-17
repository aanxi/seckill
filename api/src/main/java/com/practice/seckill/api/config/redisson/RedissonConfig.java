package com.practice.seckill.api.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Redisson分布式锁的配置
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;
//    密码为空时不能设置密码传递空密码否则会连接出错
//    @Value("${spring.redis.password}")
//    private String password;


    /**
     * 基于单节点redis的分布式锁配置
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://" + host + ":" + port;
        config.useSingleServer().setAddress(address);
        //设置watchdog每次自动续期时间
        config.setLockWatchdogTimeout(20);
//        config.useSingleServer().setPassword(password);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
