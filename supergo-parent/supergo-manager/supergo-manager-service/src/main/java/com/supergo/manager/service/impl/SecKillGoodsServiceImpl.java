package com.supergo.manager.service.impl;

import com.supergo.manager.service.SecKillGoodsService;
import com.supergo.manager.mapper.SecKillGoodsKMapper;
import com.supergo.common.pojo.SeckillGoods;
import com.supergo.common.pojo.SeckillGoodsExample;
import com.supergo.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
/**
 * 功能描述：秒杀产品service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 16:55
*/
@Service
public class SecKillGoodsServiceImpl extends BaseServiceImpl<SeckillGoods> implements SecKillGoodsService {

    @Autowired
    private SecKillGoodsKMapper secKillGoodsKMapper;

    @Override
    public int updateStatus(Long[] ids, String status) {
        SeckillGoodsExample seckillGoodsExample = new SeckillGoodsExample();
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setStatus(status);
        seckillGoodsExample.createCriteria().andIdIn(Arrays.asList(ids));
        return secKillGoodsKMapper.updateByExampleSelective(seckillGoods,seckillGoodsExample);
    }
}
