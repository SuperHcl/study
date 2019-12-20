package com.supergo.page.listener;

import com.alibaba.fastjson.JSON;
import com.supergo.common.pojo.Item;
import com.supergo.page.service.ItemPageService;
import com.supergo.user.utils.MessageInfo;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName MessageListener
 * @Description TODO
 * @Author wesker
 * @Date 7/12/2019 1:50 PM
 * @Version 1.0
 **/
public class MessageListener implements MessageListenerConcurrently {

    @Autowired
    private ItemPageService itemPageService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt msg : msgs) {
                //获取消息
                String body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                //判断消息类型
                MessageInfo messageInfo = JSON.parseObject(body, MessageInfo.class);
                //审核，
                if(messageInfo.getMethod()==1){
                    //则生成静态页
                    List<Item> items = JSON.parseArray(messageInfo.getContent().toString(),Item.class);
                    //循环生成经天也
                    Set<Long> sets = goodsIds(items);
                    for (Long goodsId : sets) {
                        itemPageService.buildHtml(goodsId);
                    }
                    //for (Item item : items) {
                    //    itemPageService.buildHtml(item.getGoodsId());
                    //}
                }else if (messageInfo.getMethod()==3){
                    //删除，则删除静态页
                    List<Long> ids = JSON.parseArray(messageInfo.getContent().toString(),Long.class);
                    //循环删除静态页
                    for (Long id : ids) {
                        itemPageService.deleteByGoodsId(id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    /***
     * 取出GoodsID重复
     * @param items
     * @return
     */
    public Set<Long> goodsIds(List<Item> items){
        Set<Long> sets = new HashSet<Long>();
        for (Item item : items) {
            sets.add(item.getGoodsId());
        }
        return sets;
    }
}
