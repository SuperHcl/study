package com.hcl.mybatis.sqlsession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author: Hucl
 * @date: 2019/7/1 09:40
 * @description:
 */
public class DocumentReader {
    /**
     * 创建document对象
     * @param inputStream 所操作的文件的输入流e.g 相关配置文件
     * @return {@link Document}
     */
    public static Document createDocument(InputStream inputStream) {
        Document document;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
