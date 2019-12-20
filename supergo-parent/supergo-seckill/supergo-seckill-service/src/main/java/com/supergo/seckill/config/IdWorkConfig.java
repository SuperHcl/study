package com.supergo.seckill.config;

import com.supergo.user.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdWorkConfig {

    @Bean
    public IdWorker idWorker(){
        IdWorker idWorker = new IdWorker(31,31);
        return idWorker;
    }
}
