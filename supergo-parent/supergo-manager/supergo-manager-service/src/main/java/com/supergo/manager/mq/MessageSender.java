package com.supergo.manager.mq;

import com.alibaba.fastjson.JSON;
import com.supergo.user.utils.MessageInfo;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    //注入DefaultMQProducer
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /***
     * 发送消息
     * MessageInfo:消息信息
     */
    public void sendMessage(MessageInfo messageInfo){
        try {
            //创建消息
            Message message = new Message(
                    messageInfo.getTopic(),
                    messageInfo.getTags(),
                    messageInfo.getKeys(),
                    (JSON.toJSONString(messageInfo)).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息
            defaultMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
