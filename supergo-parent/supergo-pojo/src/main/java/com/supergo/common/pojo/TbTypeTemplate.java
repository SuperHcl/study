package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
@Table(name = "tb_type_template")
@ApiModel(value = "tbTypeTemplate对象", description = "模板实体类")
public class TbTypeTemplate implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "id",required = true, dataType = "Long")
    private Long id;

    /**
     * 模板名称
     */
    @Column(name = "name")
    @ApiModelProperty(value = "模板名称", name = "name", dataType = "String")
    private String name;

    /**
     * 关联规格
     */
    @Column(name = "spec_ids")
    @ApiModelProperty(value = "关联规格", name = "specIds", dataType = "String")
    private String specIds;

    /**
     * 关联品牌
     */
    @Column(name = "brand_ids")
    @ApiModelProperty(value = "关联品牌", name = "brandIds", dataType = "String")
    private String brandIds;

    /**
     * 自定义属性
     */
    @Column(name = "custom_attribute_items")
    @ApiModelProperty(value = "自定义属性", name = "CustomAttrItems", dataType = "String")
    private String customAttributeItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds == null ? null : specIds.trim();
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds == null ? null : brandIds.trim();
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems == null ? null : customAttributeItems.trim();
    }
}