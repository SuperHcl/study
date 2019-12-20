package com.supergo.common.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Table(name = "tb_attribute",schema = "pinyougoudb")
public class TbAttribute implements Serializable {


    @Column(name = "attribute_id")
    @Id
    private Integer attributeId;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "attribute_key")
    private String attributeKey;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_delete")
    private String isDelete;

    @Column(name = "status")
    private Integer status;

    @Column(name = "bak1")
    private String bak1;

    @Column(name = "bak2")
    private String bak2;

    @Column(name = "bak3")
    private String bak3;

    @Column(name = "attribute_options")
    private String attributeOptions;

    private List<Map<String,String>> attrMap;

    public List<Map<String, String>> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(List<Map<String, String>> attrMap) {
        this.attrMap = attrMap;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName == null ? null : attributeName.trim();
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBak1() {
        return bak1;
    }

    public void setBak1(String bak1) {
        this.bak1 = bak1 == null ? null : bak1.trim();
    }

    public String getBak2() {
        return bak2;
    }

    public void setBak2(String bak2) {
        this.bak2 = bak2 == null ? null : bak2.trim();
    }

    public String getBak3() {
        return bak3;
    }

    public void setBak3(String bak3) {
        this.bak3 = bak3 == null ? null : bak3.trim();
    }

    public String getAttributeOptions() {
        return attributeOptions;
    }

    public void setAttributeOptions(String attributeOptions) {
        this.attributeOptions = attributeOptions == null ? null : attributeOptions.trim();
    }

    public TbAttribute(){
    }

    public TbAttribute(Map map){
        System.out.println("map:"+map);
        Integer id = (Integer) map.get("attributeId");
        if(id != null){
            System.out.println("id:"+id);
            this.attributeId = id;
        }
        this.attributeName = (String) map.get("attributeName");
        this.attributeOptions = (String) map.get("AttributeOptions");
        this.attributeKey = (String) map.get("attributeKey");
    }

    @Override
    public String toString() {
        return "TbAttribute{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeKey='" + attributeKey + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete='" + isDelete + '\'' +
                ", status=" + status +
                ", bak1='" + bak1 + '\'' +
                ", bak2='" + bak2 + '\'' +
                ", bak3='" + bak3 + '\'' +
                ", AttributeOptions='" + attributeOptions + '\'' +
                '}';
    }
}