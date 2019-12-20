package com.lock.api;

import com.lock.mapper.TbItemMapper;
import com.supergo.common.pojo.TbItem;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by on 2019/6/14.
 */
@RestController
@RequestMapping("/api/seckill")
public class SeckillController {

    //注入
    @Autowired
    private TbItemMapper itemMapper;

    //注入
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 需求：减扣库存
     * @return
     */
    @RequestMapping("/decr")
    public boolean decrstockCount(Integer productId){
        String key = "dec_store_lock_" + productId;
        //获取锁对象
        RLock lock = redissonClient.getLock(key);
    try {
        //加锁 操作很类似Java的ReentrantLock机制
        lock.lock(2, TimeUnit.MINUTES);
        TbItem item = itemMapper.selectByPrimaryKey(productId);
        //如果库存为空
        if (item.getStockCount()==0) {
            return false;
        }
        //简单减库存操作 没有重新写其他接口了
        item.setStockCount(item.getStockCount()-1);
        //更新库存
        itemMapper.updateByPrimaryKey(item);

        } catch (Exception e) {
        System.out.println(e.getMessage());
        } finally {
        //解锁
        lock.unlock();
        }
        return true;

        }

}
