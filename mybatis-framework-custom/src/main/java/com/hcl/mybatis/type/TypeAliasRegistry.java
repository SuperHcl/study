package com.hcl.mybatis.type;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author: Hucl
 * @date: 2019/7/1 16:22
 * @description: 字段类型
 */
public class TypeAliasRegistry {
    private final Map<String, Class<?>> TYPE_ALIASES = new HashMap<String, Class<?>>();

    public TypeAliasRegistry() {
        registerAlias("string", String.class);

        registerAlias("byte", Byte.class);
        registerAlias("long", Long.class);
        registerAlias("short", Short.class);
        registerAlias("int", Integer.class);
        registerAlias("integer", Integer.class);
        registerAlias("double", Double.class);
        registerAlias("float", Float.class);
        registerAlias("boolean", Boolean.class);

        registerAlias("date", Date.class);
        registerAlias("decimal", BigDecimal.class);
        registerAlias("bigdecimal", BigDecimal.class);
        registerAlias("biginteger", BigInteger.class);
        registerAlias("object", Object.class);

        registerAlias("map", Map.class);
        registerAlias("hashmap", HashMap.class);
        registerAlias("list", List.class);
        registerAlias("arraylist", ArrayList.class);
        registerAlias("collection", Collection.class);
        registerAlias("iterator", Iterator.class);
    }

    public void registerAlias(Class<?> type) {
        String alias = type.getSimpleName();

        registerAlias(alias, type);
    }

    /**
     * 解析单个引用类型 （对象）
     * @param alias 别名
     * @param value Class
     */
    public void registerAlias(String alias, Class<?> value) {
        if (alias == null) {
            throw new RuntimeException("The parameter alias cannot be null");
        }
        String key = alias.toLowerCase(Locale.ENGLISH);
        if (TYPE_ALIASES.containsKey(alias) && TYPE_ALIASES.get(alias) != null) {
            throw new RuntimeException("The alias '" + alias + "' is already mapped to the value '" + TYPE_ALIASES.get(key).getName() + "'.");
        }
        TYPE_ALIASES.put(alias, value);

    }
}
