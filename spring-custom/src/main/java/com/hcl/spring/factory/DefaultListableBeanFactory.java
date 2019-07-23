package com.hcl.spring.factory;

import com.hcl.spring.config.*;
import com.hcl.spring.converter.IntegerTypeConverter;
import com.hcl.spring.converter.StringTypeConverter;
import com.hcl.spring.converter.TypeConverter;
import com.hcl.spring.util.ReflectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/9 19:11
 * @description:
 */
public class DefaultListableBeanFactory extends AbstractBeanFactory {
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();// Map中的数据怎么来？
    private Map<String, Object> singletonObjects = new HashMap<>();

    private List<Resource> resources = new ArrayList<>();
    private List<TypeConverter> converters = new ArrayList<>();


    public DefaultListableBeanFactory(String location) {
        // 资源注册类
        registerResources();
        registerConverters();

        XmlBeanDefinitionParser xmlBeanDefinitionParser = new XmlBeanDefinitionParser();
        // 因为不清楚location字符串到底代表着是classpath下的资源，还是本地磁盘的资源或者是网络中的xml
        Resource resource = getResource(location);
        xmlBeanDefinitionParser.loadBeanDefinitions(this, resource);
    }

    public Object getBean(String beanName) {
        // 给对象起个名，在xml配置文件中，建立名称和对象的映射关系
        // 1.如果singletonObjects中已经包含了我们要找的对象，就不需要再创建了。
        // 2.如果singletonObjects中已经没有包含我们要找的对象，那么根据传递过来的beanName参数去BeanDefinition集合中查找对应的BeanDefinition信息
        // 3.根据BeanDefinition中的信息去创建Bean的实例。
        // a)根据class信息包括里面的constructor-arg通过反射进行实例化
        // b)根据BeanDefinition中封装的属性信息集合去挨个给对象赋值。
        // 类型转换
        // 反射赋值
        // c)根据initMethod方法去调用对象的初始化操作
        Object instance = singletonObjects.get(beanName);
        if (instance != null) {
            return instance;
        }
        // 只处理单例
        // 先获取BeanDefinition
        BeanDefinition beanDefinition = this.getBeanDefinitions().get(beanName);
        // 通过无参构造方法来构建instance的实例
        instance = createBeanInstance(beanDefinition.getBeanClassName(), null);
        // 给实例好的instance设置属性
        setProperty(instance, beanDefinition);

        // 初始化
        initBean(instance, beanDefinition);

        // 注册进singletonObjects
        registerSingletonObjects(beanName, instance);
        return instance;
    }

    @Override
    public List<String> getBeanNamesByType(Class<?> type) {
        List<String> result = new ArrayList<>();
        beanDefinitions.keySet().forEach(beanName -> {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                // 判断type是否是clazz的父类
                if (type.isAssignableFrom(clazz)) {
                    result.add(beanName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getBeansByType(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        beanDefinitions.values().forEach(beanDefinition -> {
            try {
                // 所有beanDefinition中的bean的类型
                Class<?> clazz1 = Class.forName(beanDefinition.getBeanClassName());
                // clazz是否是clazz1的父类型
                if (clazz.isAssignableFrom(clazz1)) {
                    String beanName = beanDefinition.getBeanName();
                    list.add((T) getBean(beanName));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return list;
    }

    @Override
    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    private void initBean(Object instance, BeanDefinition beanDefinition) {
        String initMethod = beanDefinition.getInitMethod();
        if (initMethod == null || "".equals(initMethod)) {
            return;
        }
        ReflectUtils.invokeMethod(instance, initMethod);
    }


    private void setProperty(Object instance, BeanDefinition beanDefinition) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue p : propertyValues) {
            String name = p.getName();
            // value的类型可能是TypedStringValue或者是RuntimeBeanReference
            Object value = p.getValue();

            Object valueToUse = null;
            if (value instanceof TypeStringValue) {
                TypeStringValue typeStringValue = (TypeStringValue) value;
                String stringValue = typeStringValue.getValue();
                Class<?> targetType = typeStringValue.getTargetType();

                for (TypeConverter converter : converters) {
                    if (converter.isType(targetType)) {
                        valueToUse = converter.convert(stringValue);
                    }
                }

            } else if (value instanceof RuntimeBeanReference) {
                RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
                String ref = runtimeBeanReference.getRef();
                valueToUse = getBean(ref);
            }
            ReflectUtils.setProperty(instance, name, valueToUse);
        }
    }

    private Object createBeanInstance(String beanClassName, Object... args) {
        return ReflectUtils.createObject(beanClassName, args);
    }

    private void registerSingletonObjects(String beanName, Object instance) {
        // 如果put之前存在 beanName对应的实例，则不put
        this.singletonObjects.putIfAbsent(beanName, instance);
    }


    private void registerConverters() {
        converters.add(new StringTypeConverter());
        converters.add(new IntegerTypeConverter());
    }

    private void registerResources() {
        // 提前注册号resource
        resources.add(new ClassPathResource());
    }

    private Resource getResource(String location) {
        for (Resource r : resources) {
            if (r.isCanRead(location)) {
                return r;
            }
        }
        return null;
    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName, beanDefinition);
    }
}
