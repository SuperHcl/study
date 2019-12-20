package com.supergo.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_seller")
@ApiModel(value = "seller对象", description = "商家实体类")
public class Seller implements Serializable {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "seller_id")
    @ApiModelProperty(value = "商家id", name = "sellerId", required = true, dataType = "String")
    private String sellerId;

    /**
     * 公司名
     */
    @Column(name = "name")
    @ApiModelProperty(value = "公司名称",name = "name", dataType = "String")
    private String name;

    /**
     * 店铺名称
     */
    @Column(name = "nick_name")
    @ApiModelProperty(value = "店铺名称",name = "nickName", dataType = "String")
    private String nickName;

    /**
     * 密码
     */
    @Column(name = "password")
    @ApiModelProperty(value = "密码",name = "password", dataType = "String")
    private String password;

    /**
     * EMAIL
     */
    @Column(name = "email")
    @ApiModelProperty(value = "邮箱",name = "email", dataType = "String")
    private String email;

    /**
     * 公司手机
     */
    @Column(name = "mobile")
    @ApiModelProperty(value = "公司手机",name = "mobile", dataType = "String")
    private String mobile;

    /**
     * 公司电话
     */
    @Column(name = "telephone")
    @ApiModelProperty(value = "公司电话",name = "telephone", dataType = "String")
    private String telephone;

    /**
     * 状态
     */
    @Column(name = "status")
    @ApiModelProperty(value = "商家状态",name = "status", dataType = "String")
    private String status;

    /**
     * 详细地址
     */
    @Column(name = "address_detail")
    @ApiModelProperty(value = "详细地址",name = "addressDetail", dataType = "String")
    private String addressDetail;

    /**
     * 联系人姓名
     */
    @Column(name = "linkman_name")
    @ApiModelProperty(value = "联系人姓名",name = "linkmanName", dataType = "String")
    private String linkmanName;

    /**
     * 联系人QQ
     */
    @Column(name = "linkman_qq")
    @ApiModelProperty(value = "联系人QQ",name = "linkmanQq", dataType = "String")
    private String linkmanQq;

    /**
     * 联系人电话
     */
    @Column(name = "linkman_mobile")
    @ApiModelProperty(value = "联系人电话",name = "linkmanMobile", dataType = "String")
    private String linkmanMobile;

    /**
     * 联系人EMAIL
     */
    @Column(name = "linkman_email")
    @ApiModelProperty(value = "联系人邮箱",name = "linkmanEmail", dataType = "String")
    private String linkmanEmail;

    /**
     * 营业执照号
     */
    @Column(name = "license_number")
    @ApiModelProperty(value = "营业执照号",name = "licenseNumber", dataType = "String")
    private String licenseNumber;

    /**
     * 税务登记证号
     */
    @Column(name = "tax_number")
    @ApiModelProperty(value = "税务登记证号",name = "taxNumber", dataType = "String")
    private String taxNumber;

    /**
     * 组织机构代码
     */
    @Column(name = "org_number")
    @ApiModelProperty(value = "组织机构代码",name = "orgNumber", dataType = "String")
    private String orgNumber;

    /**
     * 公司地址
     */
    @Column(name = "address")
    @ApiModelProperty(value = "公司地址",name = "address", dataType = "Long")
    private Long address;

    /**
     * 公司LOGO图
     */
    @Column(name = "logo_pic")
    @ApiModelProperty(value = "公司LOGO图",name = "logoPic", dataType = "String")
    private String logoPic;

    /**
     * 简介
     */
    @Column(name = "brief")
    @ApiModelProperty(value = "简介",name = "brief", dataType = "String")
    private String brief;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建日期",name = "createTime", dataType = "Date")
    private Date createTime;

    /**
     * 法定代表人
     */
    @Column(name = "legal_person")
    @ApiModelProperty(value = "法定代表人",name = "legalPerson", dataType = "String")
    private String legalPerson;

    /**
     * 法定代表人身份证
     */
    @Column(name = "legal_person_card_id")
    @ApiModelProperty(value = "法定代表人身份证",name = "legalPersonCardId", dataType = "String")
    private String legalPersonCardId;

    /**
     * 开户行账号名称
     */
    @Column(name = "bank_user")
    @ApiModelProperty(value = "开户行账号名称",name = "bankUser", dataType = "String")
    private String bankUser;

    /**
     * 开户行
     */
    @Column(name = "bank_name")
    @ApiModelProperty(value = "开户行",name = "bankName", dataType = "String")
    private String bankName;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户ID
     *
     * @return seller_id - 用户ID
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * 设置用户ID
     *
     * @param sellerId 用户ID
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * 获取公司名
     *
     * @return name - 公司名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置公司名
     *
     * @param name 公司名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取店铺名称
     *
     * @return nick_name - 店铺名称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置店铺名称
     *
     * @param nickName 店铺名称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取EMAIL
     *
     * @return email - EMAIL
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置EMAIL
     *
     * @param email EMAIL
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取公司手机
     *
     * @return mobile - 公司手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置公司手机
     *
     * @param mobile 公司手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取公司电话
     *
     * @return telephone - 公司电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置公司电话
     *
     * @param telephone 公司电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address_detail - 详细地址
     */
    public String getAddressDetail() {
        return addressDetail;
    }

    /**
     * 设置详细地址
     *
     * @param addressDetail 详细地址
     */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    /**
     * 获取联系人姓名
     *
     * @return linkman_name - 联系人姓名
     */
    public String getLinkmanName() {
        return linkmanName;
    }

    /**
     * 设置联系人姓名
     *
     * @param linkmanName 联系人姓名
     */
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName == null ? null : linkmanName.trim();
    }

    /**
     * 获取联系人QQ
     *
     * @return linkman_qq - 联系人QQ
     */
    public String getLinkmanQq() {
        return linkmanQq;
    }

    /**
     * 设置联系人QQ
     *
     * @param linkmanQq 联系人QQ
     */
    public void setLinkmanQq(String linkmanQq) {
        this.linkmanQq = linkmanQq == null ? null : linkmanQq.trim();
    }

    /**
     * 获取联系人电话
     *
     * @return linkman_mobile - 联系人电话
     */
    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    /**
     * 设置联系人电话
     *
     * @param linkmanMobile 联系人电话
     */
    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile == null ? null : linkmanMobile.trim();
    }

    /**
     * 获取联系人EMAIL
     *
     * @return linkman_email - 联系人EMAIL
     */
    public String getLinkmanEmail() {
        return linkmanEmail;
    }

    /**
     * 设置联系人EMAIL
     *
     * @param linkmanEmail 联系人EMAIL
     */
    public void setLinkmanEmail(String linkmanEmail) {
        this.linkmanEmail = linkmanEmail == null ? null : linkmanEmail.trim();
    }

    /**
     * 获取营业执照号
     *
     * @return license_number - 营业执照号
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * 设置营业执照号
     *
     * @param licenseNumber 营业执照号
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }

    /**
     * 获取税务登记证号
     *
     * @return tax_number - 税务登记证号
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * 设置税务登记证号
     *
     * @param taxNumber 税务登记证号
     */
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber == null ? null : taxNumber.trim();
    }

    /**
     * 获取组织机构代码
     *
     * @return org_number - 组织机构代码
     */
    public String getOrgNumber() {
        return orgNumber;
    }

    /**
     * 设置组织机构代码
     *
     * @param orgNumber 组织机构代码
     */
    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber == null ? null : orgNumber.trim();
    }

    /**
     * 获取公司地址
     *
     * @return address - 公司地址
     */
    public Long getAddress() {
        return address;
    }

    /**
     * 设置公司地址
     *
     * @param address 公司地址
     */
    public void setAddress(Long address) {
        this.address = address;
    }

    /**
     * 获取公司LOGO图
     *
     * @return logo_pic - 公司LOGO图
     */
    public String getLogoPic() {
        return logoPic;
    }

    /**
     * 设置公司LOGO图
     *
     * @param logoPic 公司LOGO图
     */
    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic == null ? null : logoPic.trim();
    }

    /**
     * 获取简介
     *
     * @return brief - 简介
     */
    public String getBrief() {
        return brief;
    }

    /**
     * 设置简介
     *
     * @param brief 简介
     */
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取法定代表人
     *
     * @return legal_person - 法定代表人
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 设置法定代表人
     *
     * @param legalPerson 法定代表人
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    /**
     * 获取法定代表人身份证
     *
     * @return legal_person_card_id - 法定代表人身份证
     */
    public String getLegalPersonCardId() {
        return legalPersonCardId;
    }

    /**
     * 设置法定代表人身份证
     *
     * @param legalPersonCardId 法定代表人身份证
     */
    public void setLegalPersonCardId(String legalPersonCardId) {
        this.legalPersonCardId = legalPersonCardId == null ? null : legalPersonCardId.trim();
    }

    /**
     * 获取开户行账号名称
     *
     * @return bank_user - 开户行账号名称
     */
    public String getBankUser() {
        return bankUser;
    }

    /**
     * 设置开户行账号名称
     *
     * @param bankUser 开户行账号名称
     */
    public void setBankUser(String bankUser) {
        this.bankUser = bankUser == null ? null : bankUser.trim();
    }

    /**
     * 获取开户行
     *
     * @return bank_name - 开户行
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置开户行
     *
     * @param bankName 开户行
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }
}