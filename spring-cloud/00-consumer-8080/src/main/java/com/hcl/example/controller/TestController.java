package com.hcl.example.controller;

import com.hcl.example.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/10/18 15:06
 * @description:
 */
@RestController
@RequestMapping("/test")
@Slf4j(topic = "test-consumer")
public class TestController {

    public static final String SERVICE_PROVIDER = "http://hcl-provider-8081";

    private final RestTemplate template;
    private final DiscoveryClient client;
    @Autowired
    public TestController(RestTemplate template, DiscoveryClient client) {
        this.template = template;
        this.client = client;
    }

    @GetMapping("/{age}/{name}")
    public boolean testHandler(@PathVariable("age") int age, @PathVariable("name") String name) {
        User user = new User().setAge(age).setName(name);
        String url = SERVICE_PROVIDER + "/provider/save";
        return template.postForObject(url, user, Boolean.class);
    }

    @GetMapping("/discovery")
    public List<String> discoveryHandler() {
        List<String> services = client.getServices();
        services.forEach(serviceId -> {
            // 获取指定名称的所有服务提供者
            List<ServiceInstance> instances = client.getInstances(serviceId);
            instances.forEach(serviceInstance -> {
                // 获取服务id，即eureka.instance.instance-id
                String serviceId1 = serviceInstance.getServiceId();
                // 获取服务提供者的URI 主机名 端口号等信息
                URI uri = serviceInstance.getUri();
                int port = serviceInstance.getPort();
                String host = serviceInstance.getHost();
                log.info("serviceId:{}", serviceId);
                log.info("instance-id:{} URI: {}, host: {}, port:{}", serviceId1, uri, host, port);
            });
        });
        return services;
    }

}
