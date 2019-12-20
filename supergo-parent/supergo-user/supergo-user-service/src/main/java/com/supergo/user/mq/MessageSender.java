package com.supergo.user.mq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Map;
import java.util.UUID;

// @Component
public class MessageSender {

    /****
     * 注入消息发送对象
     */
    private DefaultMQProducer defaultMQProducer;

    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }

    /****
     * 发送消息
     * @param messageInfo
     */
    public void sendMessage(Map<String,String> messageInfo){
        try {
            //将数据发送RocketMQ
            Message message = new Message(
                    "Topic-Mobile-Message",
                    "TagsMessage",
                    UUID.randomUUID().toString(),
                    (JSON.toJSONString(messageInfo)).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            defaultMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
