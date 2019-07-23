package com.hcl.spring.aware;

import com.hcl.spring.factory.BeanFactory;

/**
 * @author: Hucl
 * @date: 2019/7/23 17:07
 * @description:
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory);
}
