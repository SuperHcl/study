package com.supergo.manager.service;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.service.base.BaseService;
/**
 * 功能描述：秒杀产品service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 16:55
*/
public interface SecKillGoodsService extends BaseService<SeckillGoods> {

    int updateStatus(Long[] ids, String status);
}
