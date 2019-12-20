package com.supergo.seckill.service;

import com.supergo.common.pojo.SeckillOrder;
import com.supergo.http.HttpResult;
import com.supergo.service.base.BaseService;
import com.supergo.user.utils.SeckillStatus;

public interface SecKillOrderService extends BaseService<SeckillOrder> {

    SeckillStatus getStatusByUserName(String userId);

    void addSeckillOrder(String userId, Long seckillId);

    SeckillOrder getOrderByUserName(String userId);

    void updateStatusSeckillOrder(String userId, String transactionId, String payTime);

    void deleteOrder(String userId);
}
