package com.hcl.mybatis.config;

/**
 * @author: Hucl
 * @date: 2019/7/2 14:27
 * @description:
 */
public class ParameterMapping {
    // 可以理解为每个字段的字段名
    private String name;

    public ParameterMapping(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
