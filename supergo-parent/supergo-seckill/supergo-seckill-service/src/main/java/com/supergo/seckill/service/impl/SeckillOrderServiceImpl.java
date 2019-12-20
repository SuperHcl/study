package com.supergo.seckill.service.impl;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.common.pojo.SeckillOrder;
import com.supergo.http.HttpResult;
import com.supergo.seckill.mapper.SeckillGoodsMapper;
import com.supergo.seckill.mapper.SeckillOrderMapper;
import com.supergo.seckill.task.MultiThreadingCreateOrder;
import com.supergo.seckill.utils.RedisUtils;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.seckill.service.SecKillOrderService;
import com.supergo.user.utils.DateUtil;
import com.supergo.user.utils.SeckillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SeckillOrderServiceImpl extends BaseServiceImpl<SeckillOrder> implements SecKillOrderService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MultiThreadingCreateOrder multiThreadingCreateOrder;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /***
     * 获取秒杀状态
     * @param userId
     * @return
     */
    @Override
    public SeckillStatus getStatusByUserName(String userId) {

        SeckillStatus seckillStatus =  (SeckillStatus) redisUtils.hget("seckillGoods_Order_Queue_Tag", userId);

        //如果状态为秒杀失败，需要清理秒杀排队标记
        if(seckillStatus!=null && seckillStatus.getStatus().intValue()==4){

            redisUtils.hdel("seckillGoods_Order_Queue_Tag",userId);
            //redisTemplate.boundHashOps("seckillGoods_Order_Queue_Tag").delete(username);
        }

        return seckillStatus;
    }

    @Transactional
    @Override
    public void addSeckillOrder(String userId, Long seckillId) {
        //获取用户抢单的信息
        Object order = redisUtils.hget("seckillOrder", userId);
        if(order!=null){
            throw new RuntimeException("存在未支付订单！");
        }
        //排队标示
        SeckillStatus checkSeckillStatus = (SeckillStatus) redisUtils.hget("seckillGoods_Order_Queue_Tag", userId);
        if(checkSeckillStatus!=null && checkSeckillStatus.getStatus()==1){
            throw new RuntimeException("您已经在抢购商品！");
        }
        //查询商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisUtils.hget("seckillGoods",String.valueOf(seckillId));
        //判断当前商品是否有库存
        if(seckillGoods==null || seckillGoods.getStockCount()<=0){
            throw new RuntimeException("已售罄");
        }
        //创建秒杀队列数据，秒杀排队
        SeckillStatus seckillStatus = new SeckillStatus(userId, new Date(), 1, seckillId);

        //将秒杀数据存入到队列
        redisUtils.lpush("seckillGoods_Order_Queue_Up",seckillStatus);
        //存一个抢购标示,方便做状态查询
        redisUtils.hset("seckillGoods_Order_Queue_Tag", userId,seckillStatus);
        //开启异步方法调用,多线程下单
        multiThreadingCreateOrder.createOrder();
    }

    /**
     * 获取待支付的订单信息
     * @param userId
     * @return
     */
    @Override
    public SeckillOrder getOrderByUserName(String userId) {
      //获取支付订单
      SeckillOrder seckillOrder =   (SeckillOrder) redisUtils.hget("seckillOrder", userId);
      return seckillOrder;
    }

    @Override
    public void updateStatusSeckillOrder(String userId, String transactionId, String payTime) {
        //获取秒杀订单
        SeckillOrder seckillOrder = (SeckillOrder) redisUtils.hget("seckillOrder", userId);
        //修改状态（支付）
        seckillOrder.setStatus("1");
        seckillOrder.setPayTime(DateUtil.str2Date(payTime));
        seckillOrder.setTransactionId(transactionId);
        //入库(MYSQL)
        seckillOrderMapper.insertSelective(seckillOrder);
        //删除Redis中订单缓存
        redisUtils.hdel("seckillOrder", userId);
        //标识换成已支付
        SeckillStatus seckillStatus = (SeckillStatus) redisUtils.hget("seckillGoods_Order_Queue_Tag", userId);
        seckillStatus.setStatus(5);
        redisUtils.hset("seckillGoods_Order_Queue_Tag", userId, seckillStatus);
    }

    /**
     * 订单关闭，订单超时，删除订单
     * @param userId
     */
    @Override
    public void deleteOrder(String userId) {
        //获取订单信息
        SeckillOrder seckillOrder = (SeckillOrder) redisUtils.hget("seckillOrder", userId);
        //订单信息删除
        redisUtils.hdel("seckillOrder", userId);
        //获取商品信息
        SeckillGoods seckillGoods = (SeckillGoods) redisUtils.hget("seckillGoods", String.valueOf(seckillOrder.getSeckillId()));
        //如果库存为空，则从数据库查询数据，填充数据
        if(seckillGoods==null){
            seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillOrder.getSeckillId());
        }
        //库存回滚
        seckillGoods.setStockCount(seckillGoods.getStockCount()+1);
        redisUtils.hset("seckillGoods",String.valueOf(seckillGoods.getId()),seckillGoods);
        redisUtils.hincr("seckillGoodsCount", String.valueOf(seckillGoods.getId()),1);
        //用户排队标示
        SeckillStatus seckillStatus = (SeckillStatus) redisUtils.hget("seckillGoods_Order_Queue_Tag",userId);
        seckillStatus.setStatus(3);
        redisUtils.hset("seckillGoods_Order_Queue_Tag", userId,seckillStatus);
    }


}
