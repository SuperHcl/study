package com.hcl.starter.service;

import lombok.AllArgsConstructor;

/**
 * @author: Hucl
 * @date: 2019/9/19 13:58
 * @description: 自定义starter的service
 * 完成功能：为用户提供的字符串添加前辍与后辍，而前辍与后辍定义在yml或properties配置文件中。
 * 例如，用户输入的字符串为China，application.yml配置文件中配置的前辍为$$$，后辍为+++，则最终生成的字符串为$$$China+++。
 */
@AllArgsConstructor
public class WrapCustomService {

    private String before;

    private String after;


    public String wrap(String word) {
        return before + word + after;
    }
}
