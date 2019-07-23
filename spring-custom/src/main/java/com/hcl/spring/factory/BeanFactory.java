package com.hcl.spring.factory;

import com.hcl.spring.config.BeanDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/8 19:22
 * @description:
 */
public interface BeanFactory {
    /**
     * 根据bean的name获取对象实例
     * @param name
     * @return
     */
    Object getBean(String name);

    /**
     * 根据bean的类型获取对相关实例
     * @param clazz
     * @return
     */
    Object getBean(Class<?> clazz);

    /**
     * 根据指定bean的类型，获取对应类型和子类型对应的bean实例
     * @param clazz 指定bean的类型
     * @param <T> 对应类型或子类型
     * @return list
     */
    <T> List<T> getBeansByType(Class<T> clazz);

    /**
     * 根据指定bean的类型，获取对应类型和子类型对应的bean的name
     * @param type
     * @return
     */
    List<String> getBeanNamesByType(Class<?> type);

    Map<String, BeanDefinition> getBeanDefinitions();
}
