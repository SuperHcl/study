package com.supergo.cart.service;

import com.supergo.common.pojo.Cart;

import java.util.List;
import java.util.Map;
/**
 * 功能描述：购物车service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:27
*/
public interface CartService {

    public List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num);
    public void addRedisCart(List<Cart> cartList, Long id);
    public List<Cart> findRedisCartList(String userId);

    public List<Cart> mergeCart(List<Cart> redisCartList, List<Cart> cookieCartList);
}
