package com.supergo.seckill.service;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.http.HttpResult;
import com.supergo.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface SeckillGoodsService extends BaseService<SeckillGoods> {

    List<SeckillGoods> list();

    HttpResult getGoodsInfo(Long seckillId);
}
