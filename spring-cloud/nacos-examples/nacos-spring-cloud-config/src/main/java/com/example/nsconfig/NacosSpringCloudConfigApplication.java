package com.example.nsconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosSpringCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosSpringCloudConfigApplication.class, args);
	}


	@RestController
	@RefreshScope
	static class TestController {
		@Value("${profile.title:yyy}")
		private String title;

		@GetMapping("/test")
		public String hello() {
			return title;
		}
	}
}
