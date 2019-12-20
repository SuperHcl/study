package com.supergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by on 2019/4/19.
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.supergo.seckill.mapper")
public class SecKillServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecKillServiceApplication.class, args);
    }
}
