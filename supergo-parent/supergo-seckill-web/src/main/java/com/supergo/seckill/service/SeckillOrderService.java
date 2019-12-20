package com.supergo.seckill.service;

import com.supergo.common.pojo.SeckillOrder;
import com.supergo.http.HttpResult;

/**
 * Created by on 2019/10/25.
 */
public interface SeckillOrderService {
    /**
     * 获取用户信息
     * @param token
     * @return
     */
    HttpResult getUserByToken(String token);

    void add(Long id, Long seckillId);

    /**
     * 秒杀订单状态查询
     * @param userId
     * @return
     */
    HttpResult getStatusByUserName(String userId);

    /**
     * 根据订单信息创建二维码
     * @param userId
     * @return
     */
    HttpResult createNativeQrcode(Long userId);
    /***
     * 查询支付状态
     * @param outtradeno:订单号
     */
    HttpResult queryPayStatus(String outtradeno,String userId);
}
