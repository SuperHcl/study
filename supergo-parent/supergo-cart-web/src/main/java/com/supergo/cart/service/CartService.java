package com.supergo.cart.service;

import com.supergo.common.pojo.Cart;

import java.util.List;

/**
 * Created by on 2019/10/30.
 */
public interface CartService {
    
    List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num);

    void addRedisCart(List<Cart> cartList, Long id);

    List<Cart> findRedisCartList(String userId);

    List<Cart> mergeCart(List<Cart> redisCartList, List<Cart> cookieCartList);
}
