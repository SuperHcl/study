package com.supergo.cart.service.impl;

import com.supergo.cart.service.CartService;
import com.supergo.common.pojo.Cart;
import com.supergo.feign.ApiCartFeign;
import com.supergo.feign.ApiGoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by on 2019/10/30.
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ApiCartFeign apiCartFeign;

    @Override
    public List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num) {

        List<Cart> carts = apiCartFeign.addItemsToCartList(cartList, itemId, num);

        return carts;
    }

    @Override
    public void addRedisCart(List<Cart> cartList, Long id) {

        apiCartFeign.addRedisCart(cartList,String.valueOf(id));

    }

    @Override
    public List<Cart> findRedisCartList(String userId) {

        List<Cart> redisCartList = apiCartFeign.findRedisCartList(userId);

        return redisCartList;
    }

    @Override
    public List<Cart> mergeCart(List<Cart> redisCartList, List<Cart> cookieCartList) {
        List<Cart> carts = apiCartFeign.mergeCart(redisCartList, cookieCartList);
        return carts;
    }
}
