package com.hcl.mybatis.sqlsession;

import com.hcl.mybatis.config.Configuration;
import com.hcl.mybatis.config.XMLConfigParser;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:19
 * @description: SqlSessionFactory构建者(构建者模式)
 */
public class SqlSessionFactoryBuilder {
    // 封装全局配置文件信息和所有映射文件的信息
    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) {
        // 解析全局配置文件，封装为Configuration对象
        // 通过InputStream流对象，去创建Document对象（dom4j）---此时没有针对xml文件中的语义进行解析
        // DocumentReader---去加载InputStream流，创建Document对象的
        Document document = DocumentReader.createDocument(inputStream);
        // 进行mybatis语义解析（全局配置文件语义解析、映射文件语义解析）
        // XMLConfigParser---解析全局配置文件
        XMLConfigParser xmlConfigParser = new XMLConfigParser(configuration);
        configuration = xmlConfigParser.parseConfiguration(document.getRootElement());
        // XMLMapperParser---解析Mapper配置文件
        return build();
    }

    public SqlSessionFactory build(Reader reader) {
        return build();
    }

    private SqlSessionFactory build() {
        return new DefaultSqlSessionFactory(configuration);
    }
}
