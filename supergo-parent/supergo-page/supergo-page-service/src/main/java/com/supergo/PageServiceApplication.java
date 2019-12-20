package com.supergo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by on 2019/4/19.
 */
// 取消数据源自动配置
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.supergo.page.mapper")
public class PageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PageServiceApplication.class, args);
    }
}
