package com.supergo.search.listener;

import com.alibaba.fastjson.JSON;
import com.supergo.common.pojo.TbItem;
import com.supergo.search.service.ItemSearchService;
import com.supergo.user.utils.MessageInfo;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/19 18:02
 *  消息监听对象
 ****/
public class GoodsMessageListener implements MessageListenerConcurrently {


    @Autowired
    private ItemSearchService itemSearchService;

    /****
     * 消息监听
     * @param msgs
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                //读取消息
                String body =new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                //获取操作类型，根据操作类型执行响应的操作
                MessageInfo messageInfo = JSON.parseObject(body, MessageInfo.class);
                //审核操作
                if(messageInfo.getMethod()==1){
                    //将商品信息导入到Elasticsearch索引库
                    List<TbItem> items = JSON.parseArray(messageInfo.getContent().toString(),TbItem.class);
                    // itemSearchService.addAll(items.toArray(new Item[items.size()]));
                    // 数据导入索引库
                }else if(messageInfo.getMethod()==3){
                    //删除操作，删除索引库中对应的数据
                    List<Long> ids = JSON.parseArray(messageInfo.getContent().toString(),Long.class);
                    itemSearchService.deleteByGoodsIds(ids.toArray(new Long[ids.size()]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
