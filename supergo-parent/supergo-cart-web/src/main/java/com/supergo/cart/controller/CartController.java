package com.supergo.cart.controller;

import com.alibaba.fastjson.JSON;
import com.supergo.cart.service.CartService;
import com.supergo.common.pojo.Cart;
import com.supergo.common.pojo.Goods;
import com.supergo.common.pojo.Item;
import com.supergo.common.pojo.User;
import com.supergo.feign.ApiGoodsFeign;
import com.supergo.feign.ApiItemFeign;
import com.supergo.feign.ApiCartFeign;
import com.supergo.feign.ApiSSOFeign;
import com.supergo.http.HttpResult;
import com.supergo.http.HttpStatus;
import com.supergo.user.utils.BeanUtils;
import com.supergo.user.utils.CollectionsUtils;
import com.supergo.user.utils.CookieUtil;
import com.supergo.user.utils.CookieUtils;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 功能描述：购物车
 *
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:27
 */

@RestController
@RequestMapping("/cart")
public class CartController {


    /**
     * 功能描述：注入购物车服务对象
     *
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:17
     */


    @Autowired
    private ApiItemFeign apiTbItemFeign;

    //注入service服务对象
    @Autowired
    private CartService cartService;

    //注入sso
    @Autowired
    private ApiSSOFeign apiSSOFeign;

    /**
     * 需求：添加购物车数据
     * 请求：http://localhost:8086/cart/addGoodsToCartList/1369377/1
     * 参数：Long itemId,Integer num
     * 返回值:PygResult
     * 业务思路：
     * 1，从request中获取用户身份信息
     * 2，查询购物车列表数据
     * 3，添加购物车数据（把新的数据添加到原来的购物车列表中）
     * 4，判断用户是否处于登录状态
     * 5，登录，把购物车列表添加到redis购物车
     * 6，否则没有登录，把购物车列表添加到cookie购物车
     * 购物车列表数据结构：
     * 思路：用户可以购买多个商家的多个商品
     * 1，购物车中有多个商家
     * 2，每一个商家可能具有多个商品
     * List<商家（购物商品集合）>
     * List<Cart>
     * CrossOrigin:必须满足spirng 4.2以上支持
     */
    @RequestMapping("addGoodsToCartList/{itemId}/{num}")
    public HttpResult addGoodsToCartList(@PathVariable Long itemId,
                                        @PathVariable Integer num,
                                        HttpServletRequest request,HttpServletResponse response) {
        try {
            //从cookie获取token
            String token = CookieUtils.getCookieValue(request, "SUPERGO_TOKEN");

            User user = apiSSOFeign.getUserByToken(token);

            //2，查询购物车列表数据
            List<Cart> cartList = this.findCartList(request, response);

            //3，添加购物车数据（把新的数据添加到原来的购物车列表中）
            cartList = cartService.addItemsToCartList(cartList, itemId, num);

            //4,判断用户此时是否登录
            if (user.getUsername() != null && !"".equals(user.getUsername())) {
                //登录状态，把购物车列表放入redis购物车
                cartService.addRedisCart(cartList, user.getId());
            } else {
                //未登录
                CookieUtils.setCookie(request,
                        response,
                        "cookie_cart",
                        JSON.toJSONString(cartList),
                        172800,
                        true);

            }

            //添加购物车成功
            return HttpResult.ok("购物车添加成功");

        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error(HttpStatus.SC_FORBIDDEN,"添加购物车失败");
        }


    }


    @RequestMapping("findCartList")
    public List<Cart> findCartList(HttpServletRequest request, HttpServletResponse response) {

        //从cookie获取token
        String token = CookieUtils.getCookieValue(request, "SUPERGO_TOKEN");

        User user = apiSSOFeign.getUserByToken(token);

        //查询cookie购物车
        String cookie_cart =
                CookieUtils.getCookieValue(request, "cookie_cart", true);

        //判断cookie购物车数据是否为空
        if (cookie_cart == null || "".equals(cookie_cart)) {
            cookie_cart = "[]";
        }
        //把cookie购物车转换成集合对象
        List<Cart> cookieCartList = JSON.parseArray(cookie_cart, Cart.class);


        //判断用户是否登录
        if (user!=null) {

            //登录状态
            //查询redis购物车
            List<Cart> redisCartList = cartService.findRedisCartList(String.valueOf(user.getId()));

            //判断cookie购物车是否存在
            if (cookieCartList != null && cookieCartList.size() > 0) {
                //合并购物车
                //把cookie购物车列表数据合并到redis购物车列表
                redisCartList = cartService.mergeCart(redisCartList, cookieCartList);
                //添加到redis购物车
                cartService.addRedisCart(redisCartList, user.getId());
                //清空cookie购物车
                CookieUtils.setCookie(request, response,
                        "cookie_cart",
                        "",
                        0,
                        true);

            }


            return redisCartList;


        } else {
            //未登录
            return cookieCartList;

        }

    }
    /**
     * 功能描述：业务查询(库存状态 & 商品是否在售)
     *
     * @Param [maps]
     * @Return void
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:17
     */
    private Map<String, List<Map<String, Object>>> processCartList(List<Map<String, String>> maps) throws Exception {
        List<Item> items = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, List<Map<String, Object>>> finalMap = new HashMap<>();
        if (Collections.isEmpty(maps)) {
            return new HashMap<>();
        }
        for (Map<String, String> map : maps) {
            Integer skuId = Integer.valueOf(map.get("skuid"));
            Integer count = Integer.valueOf(map.get("count"));
            //item
            HttpResult itemResult = this.apiTbItemFeign.getById(skuId.longValue());
            Item item = BeanUtils.convertMapToObject((Map) itemResult.getData(), Item.class);
            item.setCount(count);
            //goods
            // HttpResult goodsResult = this.apiGoodsFeign.getById(item.getGoodsId());
            // Goods goods = BeanUtils.convertMapToObject((Map) goodsResult.getData(), Goods.class);
            /*//库存状态｛1=有货，0=无货｝
            int stockStatus = item.getStockCount() > 0 ? 1 : 0;
            //商品是否在售｛1=在售，0=下架｝
            int saleStatus = "0".equals(goods.getIsMarketable()) ? 0 : 1;
            map.put("stockStatus", stockStatus + "");
            map.put("saleStatus", saleStatus + "");*/
            items.add(item);
        }
        List<List<Item>> lists = CollectionsUtils.groupList(items, Comparator.comparing(t -> t.getSellerId()));
        for (List<Item> itemList : lists) {
            Map<String, Object> map = new HashMap<>();
            map.put("shop", itemList.get(0).getSeller());
            map.put("orderItems", itemList);
            mapList.add(map);
        }
        finalMap.put("orders", mapList);
        return finalMap;
    }
}
