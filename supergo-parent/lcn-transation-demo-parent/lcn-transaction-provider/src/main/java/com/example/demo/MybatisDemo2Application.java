package com.example.demo;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@SpringBootApplication
@EnableDiscoveryClient
@EnableDistributedTransaction
public class MybatisDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemo2Application.class, args);
	}


}
