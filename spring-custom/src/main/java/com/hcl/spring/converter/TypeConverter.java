package com.hcl.spring.converter;

public interface TypeConverter {

	boolean isType(Class<?> clazz);
	
	Object convert(String source);
}
