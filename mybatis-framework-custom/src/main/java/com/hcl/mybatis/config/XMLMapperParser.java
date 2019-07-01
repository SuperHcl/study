package com.hcl.mybatis.config;


import org.dom4j.Element;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/1 10:01
 * @description: mapper文件的解析
 */
public class XMLMapperParser {
    private Configuration configuration;

    private String namespace;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }


    @SuppressWarnings("all")
    public void parse(Element rootElement) {
        // 将select标签解析为MappedStatement对象
        namespace = rootElement.attributeValue("namespace");
        List<Element> curdElements = rootElement.elements("select|update|delete|insert");
        for (Element e : curdElements) {
            parseOperatorSQL(e);
        }

        // 将解析出来的MappedStatement对象放入Configuration对象中的map集合
    }

    private void parseOperatorSQL(Element element) {
        String id = element.attributeValue("id");
        // todo 封装成MappedStatement对象 塞进Configuration#mappedStatementMap
        String parameterType = element.attributeValue("parameter");
        String resultType = element.attributeValue("resultType");
        String statementType = element.attributeValue("statementType");
        // todo 解析sql语句 # $
        String operatorValue = element.getStringValue().trim();

        configuration.mappedStatementMap.put(namespace+id, null);
    }


}
