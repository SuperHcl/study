package com.supergo.seckill.listener;

import com.alibaba.fastjson.JSON;
import com.supergo.common.pojo.SeckillGoods;
import com.supergo.common.pojo.SeckillOrder;
import com.supergo.seckill.mapper.SeckillGoodsMapper;
import com.supergo.seckill.mapper.SeckillOrderMapper;
import com.supergo.user.utils.MessageInfo;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 16:14
 * 做商品订单未支付超时回滚
 ****/
public class SeckillOrderMessageListener implements MessageListenerConcurrently {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;


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
                //消息内容
                String body = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);

                //MessageInfo
                MessageInfo messageInfo = JSON.parseObject(body, MessageInfo.class);

                //method==1  创建订单操作
                if(messageInfo.getMethod()==1){
                    //关闭支付
                    //获取订单数据
                    SeckillOrder seckillOrder = JSON.parseObject(messageInfo.getContent().toString(),SeckillOrder.class);
                    //查询数据库中是否有订单
                    SeckillOrder dbOrder = seckillOrderMapper.selectByPrimaryKey(seckillOrder.getId());
                    //没有订单，则回滚数据
                    if(dbOrder==null){
                        //回滚数据
                        Long seckillId = dbOrder.getSeckillId();
                        //查询商品数据
                        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("SeckillGoods").get(seckillId);
                        //Redis中没有数据
                        if(seckillGoods==null){
                            //查询数据库数据
                            seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillId);
                        }
                        //将数据存入到Redis
                        seckillGoods.setStockCount(seckillGoods.getStockCount()+1);
                        redisTemplate.boundHashOps("SeckillGoods").put(seckillId,seckillGoods);
                        //递增数量
                        redisTemplate.boundHashOps("SeckillGoodsCount").increment(seckillId,1);
                        //Redis中订单
                        redisTemplate.boundHashOps("SeckillOrder").delete(dbOrder.getUserId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
