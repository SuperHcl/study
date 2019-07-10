package com.hcl.spring.config;

/**
 * @author: Hucl
 * @date: 2019/7/10 14:58
 * @description:
 */
public class TypeStringValue {
    private String value;
    private Class<?> targetType;

    public TypeStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }
}
