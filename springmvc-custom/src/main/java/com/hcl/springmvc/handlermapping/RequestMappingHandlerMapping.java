package com.hcl.springmvc.handlermapping;

import com.hcl.spring.aware.BeanFactoryAware;
import com.hcl.spring.config.BeanDefinition;
import com.hcl.spring.factory.BeanFactory;
import com.hcl.springmvc.annotation.Controller;
import com.hcl.springmvc.annotation.RequestMapping;
import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/23 17:03
 * @description: 注解方式的处理器映射器
 */
public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware {
    // 建立URL和谁的映射关系？是Controller类吗
    // 使用注解方式开发的处理器里面，其实真正的handler对象是Controller类中的Method
    // 也就是说一个url对应一个method对象，但是要执行method对象，需要Controller的对象
    // 所以我们使用一个组合类去封装Method和Controller对象或者类
    // 这个类HandlerMethod对象（这也是注解方式下的真正的handler对象）
    private Map<String, Object> urlHandlerMethodMap = new HashMap<>();

    // 通过aware接口 注入beanFactory
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void initMethod() {
        // 获取spring容器中所有的bean
        List<String> beanNames = beanFactory.getBeanNamesByType(Object.class);
        Map<String, BeanDefinition> beanDefinitionMap = beanFactory.getBeanDefinitions();
        beanNames.forEach(beanName -> {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(beanClassName);
                // 如果bean是带有@Controller注解的，在进行下一步处理
                if (isHandler(clazz)) {
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            // 获取requestMapping注解中的url
                            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                            String url = requestMapping.value();
                            // 获取该方法对象method，封装到HandlerMethod对象中
                            HandlerMethod handlerMethod = new HandlerMethod(method, beanFactory.getBean(beanName));
                            // 建立映射关系
                            urlHandlerMethodMap.put(url, handlerMethod);
                        }
                    }
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean isHandler(Class<?> clazz) {
        return clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(RequestMapping.class);
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return urlHandlerMethodMap.get(uri);
    }


}
