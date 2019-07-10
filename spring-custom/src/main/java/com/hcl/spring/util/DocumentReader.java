package com.hcl.spring.util;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class DocumentReader {

	/**
	 * 创建Document对象
	 * 
	 * @param inputStream 输入流
	 * @return {@link Document}
	 */
	public static Document createDocument(InputStream inputStream) {
		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			document = reader.read(inputStream);
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

}
