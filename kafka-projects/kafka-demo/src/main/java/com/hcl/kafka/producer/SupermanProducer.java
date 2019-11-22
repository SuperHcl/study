package com.hcl.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @author: Hucl
 * @date: 2019/11/22 16:05
 * @description: topic=superman 生产者
 */
public class SupermanProducer {
    private KafkaProducer<Integer, String> producer;

    public SupermanProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "47.93.222.229:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(properties);
    }

    public void sendMessage() {
        ProducerRecord<Integer, String> record = new ProducerRecord<>("superman", "onePunch");
        producer.send(record);
    }

    public void sendMessageWithFuture() {
        ProducerRecord<Integer, String> record = new ProducerRecord<>("superman", "twoPunch");
        producer.send(record, new Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.print("partition = "+ metadata.partition());
                System.out.print(", topic = "+ metadata.topic());
                System.out.println(", offset = "+ metadata.offset());
            }
        });
    }

}
