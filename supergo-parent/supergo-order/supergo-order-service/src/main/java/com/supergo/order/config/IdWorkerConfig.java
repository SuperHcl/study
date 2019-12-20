package com.supergo.order.config;

import com.supergo.user.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName IdWorkerConfig
 * @Description TODO
 * @Author wesker
 * @Date 6/6/2019 5:23 PM
 * @Version 1.0
 **/
@Configuration
public class IdWorkerConfig {

    @Bean
    public IdWorker idWorker(){
        IdWorker idWorker = new IdWorker(31,31);
        return idWorker;
    }
}
