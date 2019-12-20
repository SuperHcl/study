package com.supergo.manager.service;
import com.supergo.common.pojo.Goods;
import com.supergo.common.pojo.Item;
import com.supergo.page.PageResult;
import com.supergo.service.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：产品service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:47
*/
public interface GoodsService extends BaseService<Goods> {

    List<Item> getItemsByGoodsIds(List<Long> ids);

    int updateStatus(List<Long> ids, String status);

    int addGoods(Goods goods);

    int deleteGoods(List<Long> ids);

    int updateGoods(Goods goods);

    Goods getByGoodsId(Long id);

    PageResult getAllGoods(Integer pageNum, Integer size, Goods goods);

    Map<String, Object> querySpecificationOption(Long typeTemplateId);

}
