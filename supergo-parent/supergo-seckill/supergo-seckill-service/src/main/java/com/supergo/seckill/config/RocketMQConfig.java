package com.supergo.seckill.config;

import com.supergo.seckill.listener.MessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

// @Configuration
public class RocketMQConfig {


    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        Map<String, String> map = new HashMap<>();
        map.put("TOPIC_SECKILL", "*");
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(2);
        defaultMQPushConsumer.setConsumerGroup("pinyougou-seckill-page-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQPushConsumer.setMessageListener(messageListener());
        defaultMQPushConsumer.setSubscription(map);
        return defaultMQPushConsumer;
    }

    @Bean
    public MessageListener messageListener(){
        return new MessageListener();
    }
}
