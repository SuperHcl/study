package com.supergo.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.supergo.seckill.page.PageCreateServiceImpl;
import com.supergo.user.utils.MessageInfo;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.List;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 16:14
 *
 ****/
public class MessageListener implements MessageListenerConcurrently {

    @Autowired
    private PageCreateServiceImpl pageCreateService;

    /***
     * 消息监听操作
     * @param msgs
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                //MessageInfo
                String body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                MessageInfo messageInfo = JSON.parseObject(body, MessageInfo.class);
                //审核操作
                if(messageInfo.getMethod()==1){
                    //获取商品ID
                    List<Long> ids = JSON.parseArray(messageInfo.getContent().toString(),Long.class);
                    for (Long id : ids) {
                        pageCreateService.buildHtml(id);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
