package com.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by on 2019/6/14.
 */
@Configuration
public class RedissonConfig {

    private static Config config = new Config();
    private static RedissonClient redissonClient = null;

    @Bean
    public RedissonClient getRedisson(){


        /*config.useClusterServers() //这是用的集群server
        .setScanInterval(2000) //设置集群状态扫描时间
        .setMasterConnectionPoolSize(10000) //设置连接数
        .setSlaveConnectionPoolSize(10000)
        .addNodeAddress("127.0.0.1:6379");*/


        config.useSingleServer().setAddress("redis://192.168.66.66:6379");
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;

    }

}
