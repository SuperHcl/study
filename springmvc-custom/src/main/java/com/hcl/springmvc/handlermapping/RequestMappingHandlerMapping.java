package com.hcl.springmvc.handlermapping;

import com.hcl.spring.aware.BeanFactoryAware;
import com.hcl.spring.config.BeanDefinition;
import com.hcl.spring.factory.BeanFactory;
import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/23 17:03
 * @description: 注解方式的处理器映射器
 */
public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware {
    // 通过aware接口 注入beanFactory
    private BeanFactory beanFactory;

    private Map<String, Object> urlHandlerMap = new HashMap<>();

    public void initMethod() {
        // 读取spring容器，获取bean信息，该bean的name属性就是url，该bean对象就是该url对应的对象
        // 通过bean工厂，获取所有的bean对象（BeanDefinition对象）
        Map<String, BeanDefinition> definitionMap = beanFactory.getBeanDefinitions();
        // 通过读取BeanDefinition对象的name属性，去检查name值是否以 / 开头的
        definitionMap.values().forEach(beanDefinition -> {
            String beanName = beanDefinition.getBeanName();
            if (beanName != null && !"".equals(beanName) && beanName.startsWith("/")) {
                // 如果是就将该对象和name值建立映射关系
                urlHandlerMap.put(beanName, beanFactory.getBean(beanName));
            }
        });
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return urlHandlerMap.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


}
