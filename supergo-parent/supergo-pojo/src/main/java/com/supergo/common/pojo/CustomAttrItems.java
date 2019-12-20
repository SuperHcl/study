package com.supergo.common.pojo;

import java.util.List;

/**
 * @ClassName CustomAttrItems
 * @Description TODO
 * @Author wesker
 * @Date 7/24/2019 3:56 PM
 * @Version 1.0
 **/
public class CustomAttrItems {

    private Integer attributeId;
    private String attributeName;
    private String attributeKey;
    private List<String> optionNames;

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
        this.attributeName = attributeName;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public List<String> getOptionNames() {
        return optionNames;
    }

    public void setOptionNames(List<String> optionNames) {
        this.optionNames = optionNames;
    }
}