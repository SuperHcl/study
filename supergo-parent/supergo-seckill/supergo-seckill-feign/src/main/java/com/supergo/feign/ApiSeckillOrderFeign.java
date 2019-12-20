package com.supergo.feign;

import com.supergo.common.pojo.SeckillOrder;
import com.supergo.http.HttpResult;

import com.supergo.user.utils.SeckillStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 17:18
 *
 ****/
@FeignClient("supergo-seckill")
public interface ApiSeckillOrderFeign {

    @RequestMapping("/seckill/order/getStatusByUserName/{userId}")
    SeckillStatus getStatusByUserName(@RequestParam("userId") String userId);

    /****
     * 添加秒杀订单
     * @param userId : 用户ID
     * @param seckillId : 秒杀的商品ID
     */
    @RequestMapping("/seckill/order/add/{userId}/{seckillId}")
    HttpResult add(@RequestParam("userId") String userId,@RequestParam("seckillId") Long seckillId);

    /***
     * 根据登录名查询秒杀订单信息
     * @param userId
     * @return
     */
    @RequestMapping("/seckill/order/getOrderByUserName/{userId}")
    SeckillOrder getOrderByUserName(@RequestParam("userId") String userId);

    /***
     * 修改秒杀订单状态
     * @param userId
     * @param transactionId
     * @param payTime
     */
    @RequestMapping("/seckill/order/updateStatus/{userId}/{transactionId}/{payTime}")
    HttpResult updateStatus(@RequestParam("userId") String userId, @RequestParam("transactionId") String transactionId, @RequestParam("payTime") String payTime);

    /***
     * 删除订单
     * @param userId
     */
    @RequestMapping("/seckill/order/deleteOrder/{userId}")
    HttpResult deleteOrder(@RequestParam("userId") String userId);
}
