package com.example.springbootmybatis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tinylog.Logger;

@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.example.springbootmybatis.repository.mapper")
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisApplication.class, args);
        log.error("Hello World");
        log.warn("Hello World");
        log.info("Hello World");
        log.debug("Hello World");
        log.trace("Hello World");

        Logger.info("tiny log info");
        Logger.debug("tiny log debug");
        Logger.warn("tiny log warn");
        Logger.error("tiny log error");
        Logger.trace("tiny log trace");
    }

}
