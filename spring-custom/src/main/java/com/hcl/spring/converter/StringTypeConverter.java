package com.hcl.spring.converter;

public class StringTypeConverter implements TypeConverter {

	@Override
	public boolean isType(Class<?> clazz) {
		return clazz == String.class;
	}
	
	@Override
	public String convert(String source) {
		return source;
	}

}
