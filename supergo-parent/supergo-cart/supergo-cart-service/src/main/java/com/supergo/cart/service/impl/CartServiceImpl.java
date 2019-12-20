package com.supergo.cart.service.impl;

import com.supergo.cart.mapper.TbItemMapper;
import com.supergo.cart.utils.RedisUtils;
import com.supergo.cart.service.CartService;
import com.supergo.common.pojo.Cart;
import com.supergo.common.pojo.Item;
import com.supergo.common.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * 功能描述：购物车service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 14:26
*/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<Cart> addItemsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //根据id查询出商品对象数据
        Item item = itemMapper.selectByPrimaryKey(itemId);
        //判断商品是否存在
        if (item == null) {
            throw new RuntimeException("商品不存在");
        }
        //判断商品是否启用
        if (!"1".equals(item.getStatus())) {
            throw new RuntimeException("商品不可用");
        }

        //获取商家id
        String sellerId = item.getSellerId();

        //判断原有购物车列表中是否具有相同商家
        Cart cart = this.isSameCart(cartList, sellerId);

        //判断是否具有相同的商家
        if (cart != null) {
            //有相同的商家
            //判断在此商家中，是否具有相同的商品（是否已经存在购买的商品）
            //获取此商品购物列表
            List<OrderItem> orderItemList = cart.getOrderItems();
            //判断是否具有相同的商品
            OrderItem orderItem = this.isSameItem(orderItemList, itemId);

            //判断是否具有相同的商品
            if (orderItem != null) {
                //商品数量相加
                orderItem.setNum(orderItem.getNum() + num);
                //总价格重新计算
                orderItem.setTotalFee(orderItem.getNum() * orderItem.getPrice().doubleValue());

                //判断此商品的数量小于0
                if (orderItem.getNum() < 1) {
                    orderItemList.remove(orderItem);
                }

                //判断商家商品列表是否为空
                if (orderItemList.size() == 0) {
                    cartList.remove(cart);
                }


            } else {
                //没有相同的商品

                OrderItem orderItem1 = this.crreateOrderItem(item, itemId, num);


                //把新添加的商品放入此商家的购物车列表
                orderItemList.add(orderItem1);

            }


        } else {
            //没有相同商家
            //新建一个商家对象
            cart = new Cart();
            cart.setSellerId(sellerId);
            cart.setSellerName(item.getSeller());


            //新建一个集合对象，封装此商家商品
            List<OrderItem> orderItems = new ArrayList<>();

            OrderItem orderItem1 = this.crreateOrderItem(item, itemId, num);

            orderItems.add(orderItem1);

            //把此集合放入此商家对象
            cart.setOrderItems(orderItems);
            //把新的商家对象放入购物车列表
            cartList.add(cart);

        }


        return cartList;
    }

    @Override
    public void addRedisCart(List<Cart> cartList, Long id) {



    }

    @Override
    public List<Cart> findRedisCartList(String userId) {
        //从redis查询当前用户购物车数据
        List<Cart> cartList = (List<Cart>) redisUtils.hget("redis_cart",userId);
        //判断redis购物车数据是否存在
        if (cartList == null || cartList.size() == 0) {
            return new ArrayList<Cart>();
        }
        return cartList;
    }



    /**
     * 需求：添加redis购物车
     *
     * @param cartList
     * @param userId
     */
    public void addRedisCart(List<Cart> cartList, String userId) {
        //向redis添加购物车数据
        redisUtils.hset("redis_cart",userId, cartList);
    }

    /**
     * 需求：合并购物车数据
     *
     * @param redisCartList
     * @param cookieCartList
     * @return
     */
    public List<Cart> mergeCart(List<Cart> redisCartList, List<Cart> cookieCartList) {

        //定义集合，封装合并结果
        List<Cart> cartList = null;

        //遍历cookie购物车列表
        for (Cart cart : cookieCartList) {
            //获取商家的商品列表
            List<OrderItem> orderItemList = cart.getOrderItems();
            //循环商家列表
            for (OrderItem orderItem : orderItemList) {

                cartList = this.addItemsToCartList(redisCartList, orderItem.getItemId(), orderItem.getNum());

            }

        }
        return cartList;
    }

    /**
     * 设置商品数据
     *
     * @param item
     * @param itemId
     * @param num
     * @return
     */
    private OrderItem crreateOrderItem(Item item, Long itemId, Integer num) {
        //新建一个商品对象
        OrderItem orderItem1 = new OrderItem();
        //设置数据
        orderItem1.setGoodsId(item.getGoodsId());
        orderItem1.setItemId(itemId);
        orderItem1.setTitle(item.getTitle());
        orderItem1.setPrice(item.getPrice());
        orderItem1.setNum(num);
        orderItem1.setTotalFee(num * item.getPrice().doubleValue());
        orderItem1.setPicPath(item.getImage());
        orderItem1.setSellerId(item.getSellerId());

        return orderItem1;
    }

    /**
     * 判断是否具有相同的商品，（是否已经存在购买的商品）
     *
     * @param orderItemList
     * @param itemId
     * @return
     */
    private OrderItem isSameItem(List<OrderItem> orderItemList, Long itemId) {
        //循环遍历商家购物列表，判断是否具有相同的商家
        for (OrderItem orderItem : orderItemList) {
            //如果商品id和新添加的商品id相等，说明有相同的商品对象
            if (orderItem.getItemId() == itemId.longValue()) {
                return orderItem;
            }

        }

        return null;
    }

    /**
     * 需求：判断原有购物车列表中是否具有相同的商家
     *
     * @param cartList
     * @param sellerId
     * @return
     */
    private Cart isSameCart(List<Cart> cartList, String sellerId) {
        //循环原有购物车列表数据
        for (Cart cart : cartList) {
            //判断如果商家对象商家id和此商品商家id相等，说明此商品属于这个商家
            if (cart.getSellerId().equals(sellerId)) {
                return cart;
            }
        }

        return null;
    }
}
