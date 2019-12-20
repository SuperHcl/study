package com.supergo.seckill.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by on 2019/10/21.
 */
@SpringBootApplication
@EnableScheduling
public class SeckillTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillTaskApplication.class,args);
    }

}
