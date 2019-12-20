package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_order_item")
@ApiModel(value = "orderItem对象", description = "商品实体类")
public class OrderItem implements Serializable {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "Id", required = true, dataType = "Long")
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "item_id")
    @ApiModelProperty(value = "商品id", name = "itemId", required = true, dataType = "Long")
    private Long itemId;

    /**
     * SPU_ID
     */
    @Column(name = "goods_id")
    @ApiModelProperty(value = "SPU_ID", name = "goodsId", dataType = "Long")
    private Long goodsId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id", name = "orderId", required = true, dataType = "Long")
    private Long orderId;

    /**
     * 商品标题
     */
    @Column(name = "title")
    @ApiModelProperty(value = "商品标题", name = "title", dataType = "String")
    private String title;

    /**
     * 商品单价
     */
    @Column(name = "price")
    @ApiModelProperty(value = "商品单价", name = "price", dataType = "Double")
    private Double price;

    /**
     * 商品购买数量
     */
    @Column(name = "num")
    @ApiModelProperty(value = "商品购买数量", name = "num", dataType = "Integer")
    private Integer num;

    /**
     * 商品总金额
     */
    @Column(name = "total_fee")
    @ApiModelProperty(value = "商品总金额", name = "totalFee", dataType = "Double")
    private Double totalFee;

    /**
     * 商品图片地址
     */
    @Column(name = "picPath")
    @ApiModelProperty(value = "商品图片地址", name = "picPath", dataType = "String")
    private String picPath;

    @Column(name = "seller_id")
    @ApiModelProperty(value = "sellerId", name = "sellerId", dataType = "String")
    private String sellerId;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品id
     *
     * @return item_id - 商品id
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * 设置商品id
     *
     * @param itemId 商品id
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * 获取SPU_ID
     *
     * @return goods_id - SPU_ID
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置SPU_ID
     *
     * @param goodsId SPU_ID
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品标题
     *
     * @return title - 商品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题
     *
     * @param title 商品标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取商品单价
     *
     * @return price - 商品单价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置商品单价
     *
     * @param price 商品单价
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取商品购买数量
     *
     * @return num - 商品购买数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置商品购买数量
     *
     * @param num 商品购买数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品总金额
     *
     * @return total_fee - 商品总金额
     */
    public Double getTotalFee() {
        return totalFee;
    }

    /**
     * 设置商品总金额
     *
     * @param totalFee 商品总金额
     */
    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 获取商品图片地址
     *
     * @return pic_path - 商品图片地址
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * 设置商品图片地址
     *
     * @param picPath 商品图片地址
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    /**
     * @return seller_id
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
}