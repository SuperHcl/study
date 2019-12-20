package com.supergo.seckill.controller;

import com.supergo.common.pojo.User;
import com.supergo.feign.ApiWeixinPayFeign;
import com.supergo.feign.ApiSeckillOrderFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.SeckillOrder;
import com.supergo.http.HttpStatus;
import com.supergo.seckill.service.SeckillOrderService;
import com.supergo.user.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/26 16:19
 *
 ****/
@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private ApiWeixinPayFeign apiWeixinPayFeign;

    //注入service服务对象
    @Autowired
    private SeckillOrderService seckillOrderService;

    /***
     * 查询支付状态
     * @param outtradeno:订单号
     */
    @RequestMapping(value = "/queryStatus/{outtradeno}")
    public HttpResult queryStatus(@PathVariable("outtradeno") String outtradeno,HttpServletRequest request){
        //根据用户名字，查询用户的订单信息
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


        //查询支付状态
        HttpResult result1 = seckillOrderService.queryPayStatus(outtradeno,String.valueOf(user.getId()));


       return result1;
    }

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/create/native")
    public HttpResult createNative(HttpServletRequest request){
        //根据用户名字，查询用户的订单信息
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

        //根据订单信息创建二维码
       HttpResult result1 =  seckillOrderService.createNativeQrcode(user.getId());

       return result1;

    }
}
