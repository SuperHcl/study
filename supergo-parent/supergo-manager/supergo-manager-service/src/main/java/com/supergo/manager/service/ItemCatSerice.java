package com.supergo.manager.service;

import com.supergo.common.pojo.ItemCat;
import com.supergo.http.HttpResult;
import com.supergo.page.PageResult;
import com.supergo.service.base.BaseService;

import java.util.List;

/**
 * 功能描述：商品详情service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:49
*/
public interface ItemCatSerice extends BaseService<ItemCat> {

    List<ItemCat> getByParentId(Long id);

    PageResult getByParentIdAndPage(Integer page, Integer rows, Long id);

    HttpResult categorysList();

}
