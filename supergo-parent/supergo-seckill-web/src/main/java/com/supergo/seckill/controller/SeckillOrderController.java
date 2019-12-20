package com.supergo.seckill.controller;

import com.supergo.common.pojo.User;
import com.supergo.feign.ApiSeckillOrderFeign;
import com.supergo.http.HttpResult;
import com.supergo.http.HttpStatus;
import com.supergo.seckill.service.SeckillOrderService;
import com.supergo.user.utils.CookieUtil;
import com.supergo.user.utils.CookieUtils;
import com.supergo.user.utils.SeckillStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 17:32
 *
 ****/
@RestController
@RequestMapping(value = "/seckill/order")
public class SeckillOrderController {



    //注入service服务
    @Autowired
    private SeckillOrderService seckillOrderService;


    /***
     * 查询用户秒杀排队信息
     * url:/seckill/order/status
     * 秒杀的商品ID
     * 调用Service查询下单状态
     * @param id:秒杀查询的商品ID
     */
    @RequestMapping(value = "/status/{id}")
    public HttpResult status(@PathVariable Long id, HttpServletRequest request){
        //用户必须登录
        //从cookie获取token
        String token = CookieUtils.getCookieValue(request, "SUPERGO_TOKEN");

        //模拟登录
        token = "82973712-f92b-11e9-9cfd-c85b76bc15e3";

        //判断
        if(StringUtils.isBlank(token)){
            return HttpResult.error(HttpStatus.SC_FORBIDDEN,"请先登录!");
        }

        //获取用户名
        HttpResult result = seckillOrderService.getUserByToken(token);

        //判断
        if(result.getData()==null){
            return HttpResult.error(HttpStatus.SC_FORBIDDEN,"请先登录!");
        }

        //获取用户对象
        User user = (User) result.getData();

        //秒杀状态  1:排队中，2:秒杀等待支付,3:支付超时，4:秒杀失败,5:支付完成

        //调用状态查询
        SeckillStatus seckillStatus = (SeckillStatus)seckillOrderService.getStatusByUserName(String.valueOf(user.getId())).getData();
        //如果此时seckillStatus==null  || 查询的商品ID!=排队的商品ID
        if(seckillStatus==null || seckillStatus.getGoodsId().longValue()!=id.longValue()){
            //没有排队信息
            return HttpResult.error(HttpStatus.SC_NOT_FOUND,"没有排队信息");
        }else if(seckillStatus.getStatus()==2){
            //如果状态为2，抢购成功，待支付
            return HttpResult.ok("抢购成功，请支付");
        }
        //其他状态
        return HttpResult.error(HttpStatus.SC_EXPECTATION_FAILED,String.valueOf(seckillStatus.getStatus()));
    }

    /***
     * 添加订单信息
     * @return
     */
    @RequestMapping(value = "/add/{seckillId}")
    public HttpResult add(@PathVariable Long seckillId, HttpServletRequest request){
        try {
            //从cookie获取token
            String token = CookieUtils.getCookieValue(request, "SUPERGO_TOKEN");
            //模拟登录
            token = "82973712-f92b-11e9-9cfd-c85b76bc15e3";
            //判断
            if(StringUtils.isBlank(token)){
                return HttpResult.error(HttpStatus.SC_FORBIDDEN,"请先登录!");
            }

            //获取用户名
            HttpResult result = seckillOrderService.getUserByToken(token);

            //判断
            if(result.getData()==null){
                return HttpResult.error(HttpStatus.SC_FORBIDDEN,"请先登录!");
            }

            //获取用户对象
            User user = (User) result.getData();

            //提交订单
            seckillOrderService.add(user.getId(),seckillId);
            return HttpResult.ok("抢购成功");
        } catch (Exception e) {
            //抢购异常
            return  HttpResult.error(e.getMessage());
        }
    }
}
