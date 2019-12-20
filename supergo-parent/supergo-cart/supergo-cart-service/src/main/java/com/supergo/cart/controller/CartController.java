package com.supergo.cart.controller;

import com.supergo.common.pojo.Cart;
import com.supergo.http.HttpResult;
import com.supergo.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：购物车
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:25
*/
@RestController
@RequestMapping("/cart")
public class CartController {

    /**
     * 功能描述：注入购物车service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 14:25
    */
    @Autowired
    private CartService cartService;

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
    /**
     * 功能描述：添加购物车
     * @Param [cartMap, request]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 14:26
    */
    @RequestMapping("/addCart")
    public HttpResult addItemsToCartList(@RequestBody List<Cart> cartList, Long itemId, Integer num){
        try {
            // 2) 获取token
            // String token = request.getHeader("Authorization");
            //3) 解析token
            // UserInfo user = JwtUtils.getInfoFromToken(token, "czxy");
            //4）调用服务层方法，实现购物车添加
            // cartService.addCart(user.getId(),cartMap,cartList);
            List<Cart> carts = cartService.addItemsToCartList(cartList, itemId, num);
            //添加成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //添加失败
            return HttpResult.error();
        }
    }


    /**
     * 需求：查询购物车列表
     * 请求：/cartList
     * 参数：map
     * 返回值：List<Map>
     * 1）获取token
     * 2) 解析token，获取用户id
     * 3）查询该用户购物车列表数据
     */
    /**
     * 功能描述：查询购物成列表
     * @Param [request]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 14:26
    */
    @RequestMapping("/cartList")
    public HttpResult cartList(HttpServletRequest request){
        try {
            //1）获取token
            // String token = request.getHeader("Authorization");
            //2) 解析token，获取用户id
            // UserInfo user = JwtUtils.getInfoFromToken(token, "czxy");
            // Integer userId = user.getId();
            String userId = "";
            //3）查询该用户购物车列表数据
            List<Cart> cartList = cartService.findRedisCartList(userId);
            return HttpResult.ok(cartList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("null");
    }


    /*@RequestMapping("/deprecated/addcart")
    @Deprecated
    public HttpResult addCart(@RequestParam("itemId") Long itemId,@RequestParam("num") Integer num,@RequestBody Cart[] carts){
        cartService.addCart(itemId, num, carts);
        return HttpResult.ok("方法过期");
    }*/

    /**
     *  合并购物车
     * @param mergeCart
     * @return
     */
   /* @RequestMapping("/mergeCartList")
    public HttpResult mergeCartList(@RequestBody MergeCart mergeCart){
        List<Cart> carts = cartService.mergeCartList(mergeCart);
        return HttpResult.ok(carts);
    }*/
}
