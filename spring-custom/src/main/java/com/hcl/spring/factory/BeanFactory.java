package com.hcl.spring.factory;

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
}
