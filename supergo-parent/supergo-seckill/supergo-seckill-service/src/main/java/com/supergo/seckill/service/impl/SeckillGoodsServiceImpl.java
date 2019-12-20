package com.supergo.seckill.service.impl;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.http.HttpResult;
import com.supergo.seckill.utils.RedisUtils;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SeckillGoodsServiceImpl extends BaseServiceImpl<SeckillGoods> implements SeckillGoodsService {

    @Autowired
    private RedisUtils redisUtils;


    //注入
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<SeckillGoods> list() {
        return redisUtils.hvalues("seckillGoods");
    }

    /**
     * 商品详情
     * @param seckillId
     * @return
     * 秒杀详情：
     * 1、剩余时间
     * 2、剩余库存
     * 3、商品信息
     */
    @Override
    public HttpResult getGoodsInfo(Long seckillId) {

        //获取商品个数
        Object result = redisUtils.hget("seckillGoodsCount", String.valueOf(seckillId));
        //获取商品剩余活动时间
        SeckillGoods goods = (SeckillGoods) redisUtils.hget("seckillGoods",String.valueOf(seckillId));
        //获取活动时间结束差值
        long times = goods.getEndTime().getTime() - System.currentTimeMillis();
        //存储时间和数量
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("counts",Long.valueOf(result.toString()));
        dataMap.put("times",times);
        dataMap.put("goods",goods);
        return HttpResult.ok(dataMap);
    }
}
