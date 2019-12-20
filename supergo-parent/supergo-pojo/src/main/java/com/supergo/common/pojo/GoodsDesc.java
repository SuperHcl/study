package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_goods_desc")
@ApiModel(value = "产品详情对象", description = "产品详情对象")
public class GoodsDesc implements Serializable {
    /**
     * SPU_ID
     */
    @Id
    @Column(name = "goods_id")
    @ApiModelProperty(value = "goodsId", name = "产品唯一标识", required = true, dataType = "Long")
    private Long goodsId;

    /**
     * 描述
     */
    @Column(name = "introduction")
    @ApiModelProperty(value = "introduction", name = "产品描述", dataType = "String")
    private String introduction;

    /**
     * 规格结果集，所有规格，包含isSelected
     */
    @Column(name = "specification_items")
    @ApiModelProperty(value = "specificationItems", name = "规格结果集，所有规格，包含isSelected", dataType = "String")
    private String specificationItems;

    /**
     * 自定义属性（参数结果）
     */
    @Column(name = "custom_attribute_items")
    @ApiModelProperty(value = "customAttributeItems", name = "自定义属性（参数结果）", dataType = "String")
    private String customAttributeItems;


    @Column(name = "item_images")
    @ApiModelProperty(value = "itemImages", name = "商品图片", dataType = "String")
    private String itemImages;

    /**
     * 包装列表
     */
    @Column(name = "package_list")
    @ApiModelProperty(value = "packageList", name = "包装列表", dataType = "String")
    private String packageList;

    /**
     * 售后服务
     */
    @Column(name = "sale_service")
    @ApiModelProperty(value = "saleService", name = "售后服务", dataType = "String")
    private String saleService;

    private static final long serialVersionUID = 1L;

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
     * 获取描述
     *
     * @return introduction - 描述
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置描述
     *
     * @param introduction 描述
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * 获取规格结果集，所有规格，包含isSelected
     *
     * @return specification_items - 规格结果集，所有规格，包含isSelected
     */
    public String getSpecificationItems() {
        return specificationItems;
    }

    /**
     * 设置规格结果集，所有规格，包含isSelected
     *
     * @param specificationItems 规格结果集，所有规格，包含isSelected
     */
    public void setSpecificationItems(String specificationItems) {
        this.specificationItems = specificationItems == null ? null : specificationItems.trim();
    }

    /**
     * 获取自定义属性（参数结果）
     *
     * @return custom_attribute_items - 自定义属性（参数结果）
     */
    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    /**
     * 设置自定义属性（参数结果）
     *
     * @param customAttributeItems 自定义属性（参数结果）
     */
    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems == null ? null : customAttributeItems.trim();
    }

    /**
     * @return item_images
     */
    public String getItemImages() {
        return itemImages;
    }

    /**
     * @param itemImages
     */
    public void setItemImages(String itemImages) {
        this.itemImages = itemImages == null ? null : itemImages.trim();
    }

    /**
     * 获取包装列表
     *
     * @return package_list - 包装列表
     */
    public String getPackageList() {
        return packageList;
    }

    /**
     * 设置包装列表
     *
     * @param packageList 包装列表
     */
    public void setPackageList(String packageList) {
        this.packageList = packageList == null ? null : packageList.trim();
    }

    /**
     * 获取售后服务
     *
     * @return sale_service - 售后服务
     */
    public String getSaleService() {
        return saleService;
    }

    /**
     * 设置售后服务
     *
     * @param saleService 售后服务
     */
    public void setSaleService(String saleService) {
        this.saleService = saleService == null ? null : saleService.trim();
    }
}