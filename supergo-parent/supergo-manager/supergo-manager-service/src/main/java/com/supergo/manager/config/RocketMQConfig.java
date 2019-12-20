package com.supergo.manager.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMQConfig {

    @Bean
    public DefaultMQProducer defaultMQProducer(){
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup("pinyougou-manager-web-prodeucer-group");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        return defaultMQProducer;
    }
}
