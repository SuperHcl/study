package com.supergo.user.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;

// @Configuration
public class RocketMQConfig {

    @Bean
    public DefaultMQProducer defaultMQProducer(){
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup("pinyougou-user-service-prodeucer-group");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        return defaultMQProducer;
    }
}
