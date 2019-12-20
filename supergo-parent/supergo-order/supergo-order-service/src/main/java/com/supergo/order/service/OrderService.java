package com.supergo.order.service;

import com.supergo.common.pojo.Order;
import com.supergo.http.HttpResult;
import com.supergo.service.base.BaseService;

/**
 * 功能描述：订单service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:34
*/
public interface OrderService extends BaseService<Order> {

    void updateStatus(String userId, String transactionId, String payTime);

    HttpResult addOrder(Order order);

    Object getPayLogByUserName(String username);

}
