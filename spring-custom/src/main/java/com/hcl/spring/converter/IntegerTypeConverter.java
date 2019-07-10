package com.hcl.spring.converter;

public class IntegerTypeConverter implements TypeConverter {

	@Override
	public Integer convert(String source) {
		return Integer.parseInt(source);
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return clazz == Integer.class;
	}

}
