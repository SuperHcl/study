package com.supergo.seckill.mq;

import com.alibaba.fastjson.JSON;
import com.supergo.user.utils.MessageInfo;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class MessageSender {

    private DefaultMQProducer producer;

    public void setProducer(DefaultMQProducer producer) {
        this.producer = producer;
    }

    /***
     * 消息发送
     */
    public void sendMessage(MessageInfo messageInfo){
        try {
            Message message = new Message(
                    messageInfo.getTopic(),
                    messageInfo.getTags(),
                    messageInfo.getKeys(),
                    JSON.toJSONString(messageInfo).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //消息延时
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(5);

            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
