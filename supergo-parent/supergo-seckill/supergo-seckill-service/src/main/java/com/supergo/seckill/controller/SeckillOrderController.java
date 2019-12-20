package com.supergo.seckill.controller;

import com.supergo.common.pojo.SeckillOrder;
import com.supergo.http.HttpResult;
import com.supergo.user.utils.SeckillStatus;
import com.supergo.seckill.service.SecKillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill/order")
public class SeckillOrderController {


    @Autowired
    private SecKillOrderService secKillOrderService;

    @RequestMapping("/getStatusByUserName/{userId}")
    public SeckillStatus getStatusByUserName(@RequestParam("userId") String userId){
        SeckillStatus status = secKillOrderService.getStatusByUserName(userId);
        return status;
    }

    /****
     * 添加秒杀订单
     * @param userId : 用户ID
     * @param seckillId : 秒杀的商品ID
     */
    @RequestMapping("/add/{userId}/{seckillId}")
    public HttpResult add(@RequestParam("userId") String userId,@RequestParam("seckillId") Long seckillId){
        try {
            secKillOrderService.addSeckillOrder(userId, seckillId);
            return HttpResult.ok("添加秒杀订单成功");
        } catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.ok("添加秒杀订单失败");
    }

    /***
     * 根据登录名查询秒杀订单信息
     * @param userId
     * @return
     */
    @RequestMapping("/getOrderByUserName/{userId}")
    public SeckillOrder getOrderByUserName(@RequestParam("userId") String userId){
        return secKillOrderService.getOrderByUserName(userId);
    }

    /***
     * 修改秒杀订单状态
     * @param userId
     * @param transactionId
     * @param payTime
     */
    @RequestMapping("/updateStatus/{userId}/{transactionId}/{payTime}")
    public HttpResult updateStatus(@RequestParam("userId") String userId, @RequestParam("transactionId") String transactionId, @RequestParam("payTime") String payTime){
        try {
            secKillOrderService.updateStatusSeckillOrder(userId, transactionId, payTime);
            return HttpResult.ok("修改秒杀订单状态成功");
        } catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.ok("修改秒杀订单状态失败");
    }

    /***
     * 删除订单
     * @param userId
     */
    @RequestMapping("/deleteOrder/{userId}")
    public HttpResult deleteOrder(@RequestParam("userId") String userId){
        try {
            secKillOrderService.deleteOrder(userId);
            return HttpResult.ok("删除订单成功");
        } catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.ok("删除订单失败");
    }
}
