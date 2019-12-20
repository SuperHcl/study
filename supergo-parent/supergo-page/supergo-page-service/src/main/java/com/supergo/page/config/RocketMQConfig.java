package com.supergo.page.config;

import com.supergo.page.listener.MessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RocketMQConfig {

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        Map<String,String> map = new HashMap<>();
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setMessageModel(MessageModel.BROADCASTING);
        defaultMQPushConsumer.setConsumerGroup("pinyougou-page-service-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQPushConsumer.setMessageListener(messageListener());
        map.put("TopicGoods","*");
        defaultMQPushConsumer.setSubscription(map);
        return defaultMQPushConsumer;
    }

    public MessageListener messageListener(){
        return new MessageListener();
    }
}
