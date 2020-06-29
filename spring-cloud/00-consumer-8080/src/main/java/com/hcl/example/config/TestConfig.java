package com.hcl.example.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Hucl
 * @date: 2019/10/18 15:03
 * @description:
 */
@Configuration
public class TestConfig {


    @Bean
    // 指定使用负载均衡方式访问（默认轮询）
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
