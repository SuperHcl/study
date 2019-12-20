package com.supergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by on 2019/3/14.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageWebApplication.class,args);
    }


}
