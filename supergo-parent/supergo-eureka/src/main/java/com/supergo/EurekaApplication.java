package com.supergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by on 2018/12/12.
 * @author hulk
 */
@SpringBootApplication
@EnableEurekaServer //表示此服务是一个注册中心服务
public class EurekaApplication {

    public static void main(String[] args) {
        //入口函数
        SpringApplication.run(EurekaApplication.class,args);
    }
}
