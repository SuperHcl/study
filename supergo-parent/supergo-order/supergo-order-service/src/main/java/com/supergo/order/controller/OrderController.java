package com.supergo.order.controller;

import com.supergo.http.HttpResult;
import com.supergo.order.service.OrderService;
import com.supergo.common.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：订单增删改查
 * @Param 
 * @Return 
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 11:41
*/
@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/23
     * @Time 11:42
    */
    @Autowired
    private OrderService orderService;


    /**
     * 功能描述：修改订单状态
     * @Param [userId, transactionId, payTime]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/23
     * @Time 14:28
    */
    @RequestMapping("/updateStatus")
    public HttpResult updateStatus(@RequestParam("userId") String userId, @RequestParam("transactionId") String transactionId, @RequestParam("payTime") String payTime) {
        try {
            orderService.updateStatus(userId, transactionId, payTime);
            return HttpResult.ok("成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.ok("失败!");
    }

    /**
     * 功能描述:添加订单
     * @Param [order]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/23
     * @Time 14:33
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Order order) {
        return HttpResult.ok(orderService.addOrder(order));
    }

    /**
     * 功能描述：根据用户名称获取支付日志
     * @Param [username]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/23
     * @Time 14:33
    */
    @RequestMapping("/getPayLogByUserName")
    public HttpResult getPayLogByUserName(@RequestParam("username") String username) {
        return HttpResult.ok(orderService.getPayLogByUserName(username));
    }
}
