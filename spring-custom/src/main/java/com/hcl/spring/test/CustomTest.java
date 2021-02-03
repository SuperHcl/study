package com.hcl.spring.test;

import com.hcl.spring.factory.BeanFactory;
import com.hcl.spring.factory.DefaultListableBeanFactory;
import com.hcl.spring.po.Student;
import org.junit.Test;

/**
 * @author: Hucl
 * @date: 2019/7/9 19:12
 * @description:
 */
public class CustomTest {

    @Test
    public void testOne() {
        // 指定资源路径
        String location = "classpath:beans.xml";

        // 创建bean工厂
        BeanFactory beanFactory = new DefaultListableBeanFactory(location);
        // 获取对象实例
        Student student = (Student) beanFactory.getBean("student");
        System.out.println(student.toString());

//        AwareTest awareTest = (AwareTest) beanFactory.getBean("awareTest");
//        awareTest.test();
    }
}
