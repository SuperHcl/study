package com.hcl.kafka;


import com.hcl.kafka.producer.SupermanProducer;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: Hucl
 * @date: 2019/11/22 16:28
 * @description:
 */
public class SupermanProducerTest {

    @Test
    public void sendMessage() throws IOException {
        SupermanProducer producer = new SupermanProducer();
        producer.sendMessage();
        System.in.read();
    }

    @Test
    public void test2() {
        SupermanProducer producer = new SupermanProducer();
        producer.sendMessageWithFuture();
    }
}
