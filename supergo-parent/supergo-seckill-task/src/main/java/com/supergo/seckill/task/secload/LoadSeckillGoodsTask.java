package com.supergo.seckill.task.secload;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.seckill.task.mapper.SecKillGoodsMapper;
import com.supergo.seckill.task.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

/**
 * Created by on 2019/10/21.
 */
@Component
@Async
public class LoadSeckillGoodsTask {

    //注入redis模板
    @Autowired
    private RedisTemplate redisTemplate;

    //
    @Autowired
    private RedisUtils redisUtils;

    //注入秒杀商品mapper
    @Autowired
    private SecKillGoodsMapper secKillGoodsMapper;


    /**
     * 查询数据库秒杀商品[符合秒杀条件的商品]
     * 并将秒杀商品添加到Reids缓存
     *
     * 查询秒杀商品
     *      1)商品必须为审核通过状态
     *      2) 活动开始时间 =< 当前时间 <=活动结束时间
     *      3)库存数量>0
     *      4)排除当前Redis中已经存在的商品(例如Redis已经存在12,256,998)
     *
     */
    @Scheduled(cron = "30 * * * * ?")
    public void pushGoodsToRedis(){


        redisTemplate.delete("seckillGoods");
        redisTemplate.delete("seckillGoodsCount");

        //创建example对象
        Example example = new Example(SeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();

        //设置查询条件
        //状态可用
        criteria.andEqualTo("status",1);
        //时间必须在活动区间
        criteria.andCondition("now() BETWEEN start_time AND end_time");
        //库存必须大于0
        criteria.andGreaterThan("stockCount",0);

        //获取Redis中所有的key,实现排除当前Redis中已经存在的商品
        Set<Long> ids = redisTemplate.boundHashOps("seckillGoods").keys();
        //判断
        if(ids!=null && ids.size()>0){
            criteria.andNotIn("id",ids);
        }

        //查询
        List<SeckillGoods> seckillGoodsList = secKillGoodsMapper.selectByExample(example);

        //判断是否有入库商品
        if(seckillGoodsList!=null && seckillGoodsList.size()>0){
            //循环
            for (SeckillGoods goods : seckillGoodsList) {

                redisUtils.hset("seckillGoods",String.valueOf(goods.getId()),goods);
                redisUtils.hset("seckillGoodsCount",String.valueOf(goods.getId()),goods.getStockCount());
               /* //存储到redis
                redisTemplate.boundHashOps("seckillGoods").put(String.valueOf(goods.getId()),goods);
                //存储库存:剩余库存存，用来防止超卖
                redisTemplate.boundHashOps("seckillGoodsCount").put(String.valueOf(goods.getId()),goods.getStockCount());*/

            }
        }





    }

}
