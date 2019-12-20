package com.supergo.common.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Table;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Document(indexName = "pinyougou",type = "item")
@Table(name = "tb_item")
@ApiModel(value = "商品对象", description = "商品对象")
public class Item implements Serializable {
    /**
     * 商品id，同时也是商品编号
     */
    @Id
    @Column(name = "id")
    @Field
    @ApiModelProperty(value = "id", name = "商品唯一标识", required = true, dataType = "Long")
    private Long id;

    /**
     * 商品标题
     */
    @Column(name = "title")
    @Field(index = true, analyzer = "ik_smart", store = true, searchAnalyzer = "ik_smart", type = FieldType.Text, copyTo = "keyword")
    @ApiModelProperty(value = "title", name = "商品标题")
    private String title;

    //规格数据
    //嵌套域
    @JSONField(serialize = false)
    @Field(index = true, type = FieldType.Auto)
    // FieldType.Nested --> Object
    private Map<String, String> specMap = new HashMap<String, String>();

    @JSONField(serialize = false)
    @Column(name = "seller")
    @Field(index = true, type = FieldType.Keyword, store = true, copyTo = "keyword")
    @ApiModelProperty(value = "seller", name = "卖家名称")
    private String seller;

    /**
     * 商品价格，单位为：元
     */
    @Column(name = "price")
    @Field(store = true, type = FieldType.Double)
    @ApiModelProperty(value = "price", name = "商品价格")
    private Double price;

    /**
     * 商品图片
     */
    @JSONField(serialize = false)
    @Column(name = "image")
    @ApiModelProperty(value = "image", name = "商品图片")
    @Field(index = false, store = true,type = FieldType.Text)
    private String image;

    @Column(name = "goods_id")
    @Field(index = true, type = FieldType.Long)
    @ApiModelProperty(value = "goodsId", name = "产品唯一标识")
    private Long goodsId;

    @JSONField(serialize = false)
    @Column(name = "category")
    @ApiModelProperty(value = "category", name = "商品分类名称")
    @Field(index = true, type = FieldType.Keyword, store = true)
    // FieldType.Keyword --> Text
    private String category;

    @JSONField(serialize = false)
    @Column(name = "brand")
    @ApiModelProperty(value = "brand", name = "商品品牌")
    // FieldType.Keyword --> Text
    @Field(index = true, type = FieldType.Keyword, store = true, copyTo = "keyword")
    private String brand;


    /**
     * 商品卖点
     */
    @ApiModelProperty(value = "sellPoint", name = "商品卖点")
    @JSONField(serialize = false)
    @Column(name = "sell_point")
    private String sellPoint;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "stockCount", name = "剩余库存")
    @Column(name = "stock_count")
    private Integer stockCount;

    /**
     * 库存数量
     */
    @JSONField(serialize = false)
    @Column(name = "num")
    @ApiModelProperty(value = "num", name = "库存数量")
    private Integer num;

    /**
     * 商品条形码
     */
    @JSONField(serialize = false)
    @Column(name = "barcode")
    @ApiModelProperty(value = "barcode", name = "商品条形码")
    private String barcode;

    /**
     * 所属类目，叶子类目
     */
    @JSONField(serialize = false)
    @Column(name = "category_id")
    @ApiModelProperty(value = "categoryid", name = "商品分类id")
    private Long categoryid;

    /**
     * 商品状态，1-正常，2-下架，3-删除
     */
    @JSONField(serialize = false)
    @Column(name = "status")
    @ApiModelProperty(value = "status", name = "商品状态")
    private String status;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    @Column(name = "create_time")
    @ApiModelProperty(value = "createTime", name = "商品创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    @Column(name = "update_time")
    @ApiModelProperty(value = "updateTime", name = "商品更新时间")
    private Date updateTime;

    @JSONField(serialize = false)
    @Column(name = "item_sn")
    @ApiModelProperty(value = "itemSn", name = "itemSn")
    private String itemSn;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "costPirce", name = "商品出售价格")
    @Column(name = "cost_pirce")
    private BigDecimal costPirce;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "marketPrice", name = "商品市场价格")
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @JSONField(serialize = false)
    @ApiModelProperty(value = "isDefault", name = "是否默认")
    @Column(name = "isDefault")
    private String isDefault;

    @JSONField(serialize = false)
    @Column(name = "seller_id")
    @ApiModelProperty(value = "sellerId", name = "商家id")
    private String sellerId;

    @JSONField(serialize = false)
    @Column(name = "cart_thumbnail")
    private String cartThumbnail;

    @Column(name = "spec")
    private String spec;

    @Transient
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private static final long serialVersionUID = 1L;

    public Map<String, String> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }

    /**
     * 获取商品id，同时也是商品编号
     *
     * @return id - 商品id，同时也是商品编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置商品id，同时也是商品编号
     *
     * @param id 商品id，同时也是商品编号
     */
    public void setId(Long id) {
        this.id = id;
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
     * 获取商品卖点
     *
     * @return sell_point - 商品卖点
     */
    public String getSellPoint() {
        return sellPoint;
    }

    /**
     * 设置商品卖点
     *
     * @param sellPoint 商品卖点
     */
    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    /**
     * 获取商品价格，单位为：元
     *
     * @return price - 商品价格，单位为：元
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置商品价格，单位为：元
     *
     * @param price 商品价格，单位为：元
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return stock_count
     */
    public Integer getStockCount() {
        return stockCount;
    }

    /**
     * @param stockCount
     */
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 获取库存数量
     *
     * @return num - 库存数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置库存数量
     *
     * @param num 库存数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品条形码
     *
     * @return barcode - 商品条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置商品条形码
     *
     * @param barcode 商品条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * 获取商品图片
     *
     * @return image - 商品图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置商品图片
     *
     * @param image 商品图片
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取所属类目，叶子类目
     *
     * @return categoryId - 所属类目，叶子类目
     */
    public Long getCategoryid() {
        return categoryid;
    }

    /**
     * 设置所属类目，叶子类目
     *
     * @param categoryid 所属类目，叶子类目
     */
    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * 获取商品状态，1-正常，2-下架，3-删除
     *
     * @return status - 商品状态，1-正常，2-下架，3-删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置商品状态，1-正常，2-下架，3-删除
     *
     * @param status 商品状态，1-正常，2-下架，3-删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return item_sn
     */
    public String getItemSn() {
        return itemSn;
    }

    /**
     * @param itemSn
     */
    public void setItemSn(String itemSn) {
        this.itemSn = itemSn == null ? null : itemSn.trim();
    }

    /**
     * @return cost_pirce
     */
    public BigDecimal getCostPirce() {
        return costPirce;
    }

    /**
     * @param costPirce
     */
    public void setCostPirce(BigDecimal costPirce) {
        this.costPirce = costPirce;
    }

    /**
     * @return market_price
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * @param marketPrice
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * @return is_default
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * @return goods_id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    /**
     * @return cart_thumbnail
     */
    public String getCartThumbnail() {
        return cartThumbnail;
    }

    /**
     * @param cartThumbnail
     */
    public void setCartThumbnail(String cartThumbnail) {
        this.cartThumbnail = cartThumbnail == null ? null : cartThumbnail.trim();
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    /**
     * @return spec
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param spec
     */
    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    /**
     * @return seller
     */
    public String getSeller() {
        return seller;
    }

    /**
     * @param seller
     */
    public void setSeller(String seller) {
        this.seller = seller == null ? null : seller.trim();
    }
}