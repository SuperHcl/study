package com.supergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by on 2019/5/23.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DruidMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruidMonitorApplication.class,args);
    }

}
