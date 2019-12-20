package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Table(name = "tb_item")
@ApiModel(value = "item对象", description = "商品实体类")
public class TbItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "Id", required = true, dataType = "Long")
    private Long id;

    /**
     * 商品标题
     */
    @Column(name = "title")
    @ApiModelProperty(value = "商品标题", name = "title",required = true, dataType = "String")
    private String title;

    /**
     * 省
     */
    @Column(name = "sell_point")
    @ApiModelProperty(value = "省", name = "sellPoint", dataType = "String")
    private String sellPoint;

    /**
     * 商品卖点
     */
    @ApiModelProperty(value = "价格", name = "price", dataType = "BigDecimal")
    private BigDecimal price;

    /**
     * 剩余库存
     */
    @Column(name = "stock_count")
    @ApiModelProperty(value = "剩余库存", name = "stockCount", dataType = "Integer")
    private Integer stockCount;

    /**
     * 库存数量
     */
    @Column(name = "num")
    @ApiModelProperty(value = "库存数量", name = "num",required = true, dataType = "Integer")
    private Integer num;

    /**
     * 商品条形码
     */
    @Column(name = "barcode")
    @ApiModelProperty(value = "商品条形码", name = "barcode",dataType = "String")
    private String barcode;

    /**
     * 商品图片
     */
    @Column(name = "image")
    @ApiModelProperty(value = "商品图片", name = "image", dataType = "String")
    private String image;

    /**
     * 所属类目，叶子类目
     */
    @ApiModelProperty(value = "所属类目", name = "categoryId",required = true, dataType = "Long")
    private Long categoryId;

    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    @Column(name = "status")
    @ApiModelProperty(value = "商品状态", name = "status",required = true, dataType = "String")
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "库存数量", name = "createTime",required = true, dataType = "Date")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", name = "updateTime",required = true, dataType = "Date")
    private Date updateTime;


    @ApiModelProperty(value = "itemSn", name = "itemSn", dataType = "String")
    private String itemSn;


    @Column(name = "cost_pirce")
    @ApiModelProperty(value = "costPirce", name = "costPirce",dataType = "BigDecimal")
    private BigDecimal costPirce;


    @Column(name = "market_price")
    @ApiModelProperty(value = "marketPrice", name = "marketPrice", dataType = "BigDecimal")
    private BigDecimal marketPrice;


    @ApiModelProperty(value = "isDefault", name = "isDefault", dataType = "String")
    private String isDefault;


    @ApiModelProperty(value = "goodsId", name = "goodsId", dataType = "Long")
    private Long goodsId;


    @ApiModelProperty(value = "sellerId", name = "sellerId", dataType = "String")
    private String sellerId;


    @Column(name = "cart_thumbnail")
    @ApiModelProperty(value = "cartThumbnail", name = "cartThumbnail", dataType = "String")
    private String cartThumbnail;


    @Column(name = "category")
    @ApiModelProperty(value = "category", name = "category", dataType = "String")
    private String category;


    @Column(name = "brand")
    @ApiModelProperty(value = "brand", name = "brand", dataType = "String")
    private String brand;


    @Column(name = "spec")
    @ApiModelProperty(value = "spec", name = "spec", dataType = "String")
    private String spec;


    @Column(name = "seller")
    @ApiModelProperty(value = "seller", name = "seller",dataType = "String")
    private String seller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getItemSn() {
        return itemSn;
    }

    public void setItemSn(String itemSn) {
        this.itemSn = itemSn == null ? null : itemSn.trim();
    }

    public BigDecimal getCostPirce() {
        return costPirce;
    }

    public void setCostPirce(BigDecimal costPirce) {
        this.costPirce = costPirce;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getCartThumbnail() {
        return cartThumbnail;
    }

    public void setCartThumbnail(String cartThumbnail) {
        this.cartThumbnail = cartThumbnail == null ? null : cartThumbnail.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller == null ? null : seller.trim();
    }
}