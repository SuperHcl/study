package com.supergo.manager.service;

import com.supergo.common.pojo.Seller;
import com.supergo.page.PageResult;
import com.supergo.service.base.BaseService;

/**
 * 功能描述：商家service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 17:01
*/
public interface SellerService extends BaseService<Seller> {

    /**
     *
     * 功能描述:  更新商家状态
     *
     * @param:
     * @return: 
     * @auther: wesker
     * @date: 6/3/2019 11:49 AM
     */
    int updateStatus(String id, String status);


    PageResult getAllSellers(Integer page, Integer rows, Seller seller);
}
