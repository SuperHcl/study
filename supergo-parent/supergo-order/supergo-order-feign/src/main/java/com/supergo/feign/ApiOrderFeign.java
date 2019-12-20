package com.supergo.feign;


import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/24 16:46
 *
 ****/
@FeignClient("supergo-order")
public interface ApiOrderFeign {

    /***
     * 修改订单状态
     * @param userId:根据用户名查询支付日志
     * @param payTime:支付时间
     * @param transactionId:微信支付交易号
     */
    @RequestMapping("/order/updateStatus")
    void updateStatus(@RequestParam("userId") String userId,@RequestParam("transactionId") String transactionId,@RequestParam("payTime") String payTime);

    /***
     * 添加订单
     * @param order
     * @return
     */
    @RequestMapping("/order/add")
    HttpResult add(@RequestBody(required = false) Order order);

    /***
     * 根据用户登录名查询用户支付日志
     * @param username
     * @return
     */
    // PayLog getPayLogByUserName(String username);

    @RequestMapping("/order/getPayLogByUserName")
    HttpResult getPayLogByUserName(@RequestParam("username") String username);
}
