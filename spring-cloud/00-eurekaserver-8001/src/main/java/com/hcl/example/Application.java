package com.hcl.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
// 开启eureka服务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
