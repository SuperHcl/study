package com.hcl.example.controller;

import com.hcl.example.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * @author: Hucl
 * @date: 2019/10/18 15:35
 * @description: 服务提供者
 */
@RestController
@Slf4j
public class TestProviderController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/save")
    public boolean saveHandle(@RequestBody User user) {
        log.info("{}", user);
        return !Objects.isNull(user);
    }

    /**
     * 列出eureka中的服务列表
     * @return List
     */
    @GetMapping("/discovery")
    public List<String> discoveryClientHandle() {
        List<String> services = discoveryClient.getServices();
        services.forEach(serviceId -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            for (ServiceInstance instance : instances) {
                log.info("host : {}, port : {}", instance.getHost(), instance.getPort());
            }
        });
        return services;
    }
}
