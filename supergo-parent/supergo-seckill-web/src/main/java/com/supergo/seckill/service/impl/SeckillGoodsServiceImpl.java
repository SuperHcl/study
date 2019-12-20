package com.supergo.seckill.service.impl;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.feign.ApiSeckillGoodsFeign;
import com.supergo.http.HttpResult;
import com.supergo.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by on 2019/10/21.
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    //注入feign
    @Autowired
    private ApiSeckillGoodsFeign seckillGoodsFeign;


    @Override
    public HttpResult getGoodsInfo(Long seckillId) {
        //调用查询方法，查询秒杀详情页商品
        HttpResult result = seckillGoodsFeign.getGoodsInfo(seckillId);
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public HttpResult list() {
        //从Redis缓存中取出所有秒杀商品数据
        HttpResult result = seckillGoodsFeign.list();
        return result;
    }
}
