package com.supergo.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/23 15:20
 *
 ****/
public class Cart implements Serializable {
    //商家名称
    private String sellerName;

    //商家ID
    private String sellerId;

    //购买该商家的商品信息  = new ()  防止控制指针
    private List<OrderItem> orderItems = new ArrayList<>();

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
