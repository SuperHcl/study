package com.hcl.mybatis.config;

import com.hcl.mybatis.sqlsession.DocumentReader;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author: Hucl
 * @date: 2019/7/1 10:01
 * @description:全局配置文件的解析
 */
public class XMLConfigParser {
    private Configuration configuration;

    public XMLConfigParser(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * SqlMapConfig.xml
     * @param element element
     * @return {@link Configuration}
     */
    public Configuration parseConfiguration(Element element) {
        // parse <environment></environment>
        parseEnvironments(element.element("environments"));
        // parse <mappers></mappers>
        parseMappers(element.element("mappers"));
        return configuration;
    }

    // <environments> 主要解析数据源
    private void parseEnvironments(Element environments) {
        String defaultId = environments.attributeValue("default");
        List<Element> environmentList = environments.elements("environment");
        for (Element envElement : environmentList) {
            String id = envElement.attributeValue("id");
            if (!StringUtils.isNullOrEmpty(id) && defaultId.equals(id)) {
                parseDataSource(envElement.element("dataSource"));
            }
        }
    }

    private void parseDataSource(Element envElement) {
        String type = envElement.attributeValue("type");
        if (StringUtils.isNullOrEmpty(type)) {
            type = "DBCP";
        }
        List<Element> propertyList = envElement.elements("property");
        Properties properties = new Properties();
        for (Element propertyEle : propertyList) {
            String name = propertyEle.attributeValue("name");
            String value = propertyEle.attributeValue("value");
            properties.setProperty(name, value);
        }
        BasicDataSource dataSource = null;
        if ("DBCP".equals(type)) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
        }
        configuration.setDataSource(dataSource);
    }

    // <mappers> 主要解析mappers
    private void parseMappers(Element element) {
        List<Element> mappers = element.elements("mapper");
        for (Element mapper : mappers) {
            parseMapper(mapper);
        }
    }
    // 解析单个mapper
    private void parseMapper(Element mapper) {
        String resource = mapper.attributeValue("resource");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        Document document = DocumentReader.createDocument(inputStream);
        XMLMapperParser xmlMapperParser = new XMLMapperParser(configuration);
        xmlMapperParser.parse(document.getRootElement());
    }
}
