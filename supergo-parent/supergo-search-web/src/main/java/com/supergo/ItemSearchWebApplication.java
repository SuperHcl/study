package com.supergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by on 2019/4/21.
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ItemSearchWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemSearchWebApplication.class,args);
    }

}
