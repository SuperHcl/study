package com.hcl.kafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * @author: Hucl
 * @date: 2019/11/22 16:34
 * @description:
 */
public class SupermanConsumer {

    private KafkaConsumer<Integer, String> consumer;

    public SupermanConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.server", "47.93.222.229:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.consumer = new KafkaConsumer<>(properties);
    }

    public void receiveMessage() {

    }
}
