package com.hcl.mybatis.config;


import com.hcl.mybatis.build.BaseBuilder;
import org.dom4j.Element;

import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/1 10:01
 * @description: mapper文件的解析
 */
public class XMLMapperParser extends BaseBuilder {
    private Configuration configuration;

    private String namespace;

    public XMLMapperParser(Configuration configuration) {
        super(configuration);
        this.configuration = configuration;
    }


    @SuppressWarnings("all")
    public void parse(Element rootElement) {
        // 将select标签解析为MappedStatement对象
        namespace = rootElement.attributeValue("namespace");

        // 将解析出来的MappedStatement对象放入Configuration对象中的map集合
        parseStatements(rootElement.elements("select"));
    }

    private void parseStatements(List<Element> elements) {

        for (Element e : elements) {
            parseStatement(e);
        }

    }

    private void parseStatement(Element element) {
        String id = element.attributeValue("id");
        id = namespace + "." + id;
        // 获取的参数类型的全路径
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);

        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);

        String statementType = element.attributeValue("statementType");
//        String operatorValue = element.getStringValue().trim();
        // 还包含#{id}占位符的SQL语句
        // 此时拿到未解析的SQL语句，还需要进行特殊解析
        // 使用面向对象思想，创建SqlSource对象（提供获取SQL语句和SQL语句中的参数这个功能）

        // 获取的SQL语句：select * from user where id = #{id}
        // 需要的SQL语句：select * from user where id = ?
        String sqlText = element.getTextTrim();
        SqlSource sqlSource = new SqlSource(sqlText);

        // 封装MappedStatement对象
        // 可以使用构建者模式去创建MappedStatement对象
        MappedStatement mappedStatement = new MappedStatement(id, parameterTypeClass, resultTypeClass, statementType, sqlSource);

        configuration.addMappedStatement(id, mappedStatement);
    }

    /**
     * 这种方式只能获取对象类型的，就连八种基本类型的封装类都获取不了
     * 局限性比较高 推荐使用{@link BaseBuilder#resolveClass(String)}
     * @param parameterType class..
     * @return Class
     */
    private static Class<?> getClassType(String parameterType) {
        if (parameterType == null || parameterType.equals("")) {
            return null;
        }
        try {
            return Class.forName(parameterType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Class<?> clazz = XMLMapperParser.getClassType("com.hcl.mybatis.test.Test1");
        System.out.println(clazz);
    }


}
