package com.hcl.spring.factory;

/**
 * @author: Hucl
 * @date: 2019/7/8 19:22
 * @description:
 */
public interface BeanFactory {
    <T> T getBean(String name);
}
