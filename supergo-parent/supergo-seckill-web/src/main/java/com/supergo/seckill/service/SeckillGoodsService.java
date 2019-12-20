package com.supergo.seckill.service;

import com.supergo.http.HttpResult;

/**
 * Created by on 2019/10/21.
 */
public interface SeckillGoodsService {

    
    HttpResult getGoodsInfo(Long seckillId);

    HttpResult list();
}
