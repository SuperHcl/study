package com.supergo.feign;

import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 17:00
 *
 ****/
@FeignClient("supergo-seckill")
public interface ApiSeckillGoodsFeign {

    /****
     * 秒杀商品集合
     */
    @RequestMapping("/seckill/goods/list")
    HttpResult list();


    /***
     * 查询商品的个数和剩余活动时间
     * @param seckillId:秒杀商品的ID
     */
    @RequestMapping(value = "/seckill/goods/query/{seckillId}",method = RequestMethod.GET)
    HttpResult getGoodsInfo(@RequestParam("seckillId") Long seckillId);

}
