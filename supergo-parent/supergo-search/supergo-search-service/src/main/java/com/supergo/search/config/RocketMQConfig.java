package com.supergo.search.config;

import com.supergo.search.listener.GoodsMessageListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RocketMQConfig {


    @Bean(initMethod = "start",destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        Map<String, String> map = new HashMap<>();
        map.put("TopicGoods", "*");
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(2);
        defaultMQPushConsumer.setConsumerGroup("pinyougou-search-service-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr("192.168.66.66:9876");
        defaultMQPushConsumer.setMessageListener(messageListener());
        defaultMQPushConsumer.setSubscription(map);
        return defaultMQPushConsumer;
    }

    @Bean
    public MessageListener messageListener(){
        return new GoodsMessageListener();
    }
}
