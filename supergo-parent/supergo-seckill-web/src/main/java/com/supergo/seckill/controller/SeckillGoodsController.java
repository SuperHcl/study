package com.supergo.seckill.controller;

import com.supergo.feign.ApiSeckillGoodsFeign;
import com.supergo.http.HttpResult;
import com.supergo.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 17:02
 *
 ****/
@RestController
@RequestMapping(value = "/seckill/goods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsService seckillGoodsService;


    /****
     * 查询商品的个数和剩余时间
     */
    @RequestMapping(value = "/query/{seckillId}",method = RequestMethod.GET)
    public HttpResult queryInfo(@PathVariable Long seckillId){
        return seckillGoodsService.getGoodsInfo(seckillId);
    }

    /****
     * 秒杀商品集合
     * @return
     */
    @RequestMapping(value = "/list")
    public HttpResult list(){
        return seckillGoodsService.list();
    }

}
