package com.hcl.spring.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/9 19:18
 * @description:
 */
public class BeanDefinition {
    private String beanClassName;
    private String beanName;
    private String initMethod;
    /**
     * bean中的property属性
     */
    private List<PropertyValue> propertyValues = new ArrayList<>();

    public BeanDefinition(String beanClassName, String beanName) {
        this.beanClassName = beanClassName;
        this.beanName = beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }
}
