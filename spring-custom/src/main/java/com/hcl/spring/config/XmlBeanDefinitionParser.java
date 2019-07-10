package com.hcl.spring.config;

import com.hcl.spring.factory.DefaultListableBeanFactory;
import com.hcl.spring.util.DocumentReader;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/7/9 19:49
 * @description:
 */
public class XmlBeanDefinitionParser {
    public void loadBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory, Resource resource) {
        // 1.读取配置文件的bean信息
        InputStream inputStream = resource.getInputStream();
        Document document = DocumentReader.createDocument(inputStream);

        XmlBeanDefinitionDocumentParser documentParser = new XmlBeanDefinitionDocumentParser(defaultListableBeanFactory);
        documentParser.loadBeanDefinitions(document.getRootElement());
    }
}
