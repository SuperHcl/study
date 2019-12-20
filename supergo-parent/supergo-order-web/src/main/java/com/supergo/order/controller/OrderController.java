package com.supergo.order.controller;

import com.supergo.feign.ApiOrderFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Order;
import com.supergo.user.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/24 16:40
 * 接收用户订单信息
 ****/
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private ApiOrderFeign apiOrderFeign;

    //注入

    /****
     * 添加订单信息
     * 支付方式
     * 收件人信息
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "/add")
    public HttpResult add(@RequestBody Order order){
        //获取用户登录名字
        // String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = "123";
        //完善订单信息 ，
        order.setOrderId(Long.valueOf(SnowflakeIdWorker.generateId()));
        order.setStatus("1");
        order.setSourceType("2");
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setUserId(userId);
        return apiOrderFeign.add(order);
    }
}
