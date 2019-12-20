package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_address")
@ApiModel(value = "address对象", description = "地址实体类")
public class Address implements Serializable {
    @Id
    @Column(name = "id")
    @ApiModelProperty(value = "id", name = "地址信息唯一标识", required = true, dataType = "Long")
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "userId", name = "用户唯一标识", dataType = "String")
    private String userId;

    /**
     * 省
     */
    @Column(name = "province_id")
    @ApiModelProperty(value = "provinceId", name = "省份唯一标识", dataType = "String")
    private String provinceId;

    /**
     * 市
     */
    @Column(name = "city_id")
    @ApiModelProperty(value = "cityId", name = "城市唯一标识", dataType = "String")
    private String cityId;

    /**
     * 县/区
     */
    @Column(name = "town_id")
    @ApiModelProperty(value = "townId", name = "区县唯一标识", dataType = "String")
    private String townId;

    /**
     * 手机
     */
    @Column(name = "mobile")
    @ApiModelProperty(value = "mobile", name = "用户手机号码", dataType = "String")
    private String mobile;

    /**
     * 详细地址
     */
    @Column(name = "address")
    @ApiModelProperty(value = "address", name = "详细地址", dataType = "String")
    private String address;

    /**
     * 联系人
     */
    @Column(name = "contact")
    @ApiModelProperty(value = "contact", name = "联系人", dataType = "String")
    private String contact;

    /**
     * 是否是默认 1默认 0否
     */
    @Column(name = "is_default")
    @ApiModelProperty(value = "isDefault", name = "是否是默认", dataType = "String")
    private String isDefault;

    /**
     * 备注
     */
    @Column(name = "notes")
    @ApiModelProperty(value = "notes", name = "备注信息", dataType = "String")
    private String notes;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    @ApiModelProperty(value = "createDate", name = "创建日期", dataType = "Date")
    private Date createDate;

    /**
     * 别名
     */
    @Column(name = "alias")
    @ApiModelProperty(value = "alias", name = "别名", dataType = "String")
    private String alias;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取省
     *
     * @return province_id - 省
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * 设置省
     *
     * @param provinceId 省
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    /**
     * 获取市
     *
     * @return city_id - 市
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 设置市
     *
     * @param cityId 市
     */
    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    /**
     * 获取县/区
     *
     * @return town_id - 县/区
     */
    public String getTownId() {
        return townId;
    }

    /**
     * 设置县/区
     *
     * @param townId 县/区
     */
    public void setTownId(String townId) {
        this.townId = townId == null ? null : townId.trim();
    }

    /**
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取联系人
     *
     * @return contact - 联系人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 设置联系人
     *
     * @param contact 联系人
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * 获取是否是默认 1默认 0否
     *
     * @return is_default - 是否是默认 1默认 0否
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否是默认 1默认 0否
     *
     * @param isDefault 是否是默认 1默认 0否
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * 获取备注
     *
     * @return notes - 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * 设置备注
     *
     * @param notes 备注
     */
    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    /**
     * 获取创建日期
     *
     * @return create_date - 创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建日期
     *
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取别名
     *
     * @return alias - 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }
}