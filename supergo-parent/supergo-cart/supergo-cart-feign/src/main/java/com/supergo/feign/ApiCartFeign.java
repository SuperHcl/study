package com.supergo.feign;

import com.supergo.common.pojo.Cart;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by on 2019/1/2.
 */
@FeignClient("supergo-cart")
public interface ApiCartFeign {
    /**
     * 需求：查询购物车列表
     * 请求：/cartList
     * 参数：map
     * 返回值：List<Cart>
     * 1）获取token
     * 2) 解析token，获取用户id
     * 3）查询该用户购物车列表数据
     */
    @RequestMapping("/cart/cartList/{userId}")
    public List<Cart> findRedisCartList(@RequestParam("userId") String userId);

    /**
     * 需求：添加购物车
     * 请求：/addcart
     * 参数：map
     * 返回值：JmypResult
     * 1）查询购物车列表
     * 2）判断购物车列表中是否存在相同的商品
     * 3）如果存在相同的商品，此商品数量相加
     * 4）否则，直接把商品添加到购物车中即可
     */
    @RequestMapping("/cart/addCart")
    public List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num);

    /**
     * 需求：添加redis购物车
     * @param cartList
     * @param username
     */
    @RequestMapping("/cart/addRedisCart")
    public void addRedisCart(List<Cart> cartList, String username);

    /**
     * 需求：合并购物车数据
     * @param redisCartList
     * @param cookieCartList
     * @return
     */
    @RequestMapping("/cart/mergeCart")
    public List<Cart> mergeCart(List<Cart> redisCartList, List<Cart> cookieCartList);
}
