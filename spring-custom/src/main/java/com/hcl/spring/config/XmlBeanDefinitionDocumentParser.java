package com.hcl.spring.config;

import com.hcl.spring.factory.DefaultListableBeanFactory;
import com.hcl.spring.util.ClassUtils;
import com.hcl.spring.util.ReflectUtils;
import org.dom4j.Element;

import java.util.List;


/**
 * @author: Hucl
 * @date: 2019/7/10 14:25
 * @description: 住啊们解析配置文件
 */
public class XmlBeanDefinitionDocumentParser {
    private DefaultListableBeanFactory beanFactory;

    public XmlBeanDefinitionDocumentParser(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Element rootElement) {
        List<Element> elements = rootElement.elements();

        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            // 判断是那种类型的标签
            if (name.equals("bean")) {
                parseDefaultElement(element);
            } else {
                // 自定义的标签
                parseCustomElement(element);
            }
        }
    }

    private void parseCustomElement(Element element) {
        if (element.getName().equals("component-scan")) {
            String packageName = element.attributeValue("package");

            List<String> beanClassNames = getBeanClassName(packageName);
            BeanDefinition beanDefinition = null;
            for (String beanClassName : beanClassNames) {
                String beanName = beanClassName.substring(beanClassName.indexOf(".") + 1);
                beanDefinition = new BeanDefinition(beanClassName, beanName);
                registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    private List<String> getBeanClassName(String packageName) {
        // 获取项目路径下指定包名下面的所有类名，最终得到类似：com.hcl.spring.util.ReflectUtils
        return ClassUtils.getClazzName(packageName, false);
    }

    private void parseDefaultElement(Element beanElement) {
        if (beanElement == null) {
            return;
        }
        try {
            String id = beanElement.attributeValue("id");
            String name = beanElement.attributeValue("name");
            String clazz = beanElement.attributeValue("class");
            Class<?> clazzType = Class.forName(clazz);
            String initMethod = beanElement.attributeValue("init-method");

            String beanName = id == null ? name : id;
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;

            // 创建BeanDefinition对象
            BeanDefinition beanDefinition = new BeanDefinition(clazz, beanName);
            beanDefinition.setInitMethod(initMethod);
            // 获取property子标签集合
            List<Element> propertyElements = beanElement.elements();
            for (Element propertyElement : propertyElements) {
                parsePropertyElement(beanDefinition, propertyElement);
            }

            // 注册BeanDefinition信息
            registerBeanDefinition(beanName, beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    /**
     * 解析property子标签
     *
     * @param beanDefinition  bd
     * @param propertyElement pe
     */
    private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
        if (propertyElement == null) {
            return;
        }
        String name = propertyElement.attributeValue("name");
        String value = propertyElement.attributeValue("value");
        String ref = propertyElement.attributeValue("ref");
        // 如果value 和 ref 都有值，则返回
        if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
            return;
        }

        /**
         * PropertyValue就封装这一个property标签的信息
         */
        PropertyValue pv = null;
        if (value != null && !value.equals("")) {
            // 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
            TypeStringValue typeStringValue = new TypeStringValue(value);

            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefinition.getBeanClassName(), name);
            typeStringValue.setTargetType(targetType);

            pv = new PropertyValue(name, typeStringValue);
            beanDefinition.addPropertyValue(pv);
        } else if (ref != null && !ref.equals("")) {
            RuntimeBeanReference reference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name, reference);
            beanDefinition.addPropertyValue(pv);
        }
    }


}
