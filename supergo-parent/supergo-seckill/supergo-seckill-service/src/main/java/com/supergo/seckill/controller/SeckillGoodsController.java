package com.supergo.seckill.controller;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.http.HttpResult;
import com.supergo.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seckill/goods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /****
     * 秒杀商品集合
     */
    @RequestMapping("/list")
    public HttpResult list(){
        List<SeckillGoods> seckillGoods = seckillGoodsService.list();
        return HttpResult.ok(seckillGoods);
    }


    /***
     * 查询商品的个数和剩余活动时间
     * @param seckillId:秒杀商品的ID
     * /seckill/goods/query/
     */
    @RequestMapping("/query/{seckillId}")
    public HttpResult getGoodsInfo(@PathVariable("seckillId") Long seckillId){
        HttpResult result = seckillGoodsService.getGoodsInfo(seckillId);
        return result;
    }

}
