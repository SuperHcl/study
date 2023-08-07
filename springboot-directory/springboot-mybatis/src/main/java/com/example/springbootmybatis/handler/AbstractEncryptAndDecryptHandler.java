package com.example.springbootmybatis.handler;

import com.example.springbootmybatis.util.ReflectUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 加解密处理
 *
 * @author Hu.ChangLiang
 * @date 2023/6/4 20:36
 */
@SuppressWarnings("all")
public abstract class AbstractEncryptAndDecryptHandler {
    private final List<String> targetFields;

    public AbstractEncryptAndDecryptHandler(List<String> targetFields) {
        this.targetFields = targetFields;
    }

    /**
     * 执行加解密操作
     *
     * @param resultContext
     */
    public void doHandle(Object resultContext) {
        if (Objects.isNull(resultContext)) {
            return;
        }
        // 解密处理逻辑
        Object originalObject = resultContext;
        if (originalObject instanceof Collection) {
            Collection<Object> list = (Collection<Object>) originalObject;
            for (Object o : list) {
                if (o instanceof Map) {
                    setValueFromMap((Map) o);
                } else {
                    setValueFromObject(o);
                }
            }
        } else if (originalObject instanceof Map) {
            // originalObject instanceof Map
            setValueFromMap((Map) originalObject);
        } else {
            // originalObject instanceof 某个具体的类
            setValueFromObject(originalObject);
        }
    }

    protected void setValueFromObject(Object o) {
        for (String targetField : targetFields) {
            Object fieldValue = ReflectUtil.getFieldValue(o, targetField);
            if (Objects.isNull(fieldValue)) {
                continue;
            }
            String encrypted = getOperatedContent(String.valueOf(fieldValue));
            ReflectUtil.setFieldValue(o, targetField, encrypted);
        }
    }

    protected void setValueFromMap(Map map) {
        for (String targetField : targetFields) {
            Object value = map.get(targetField);
            if (Objects.isNull(value)) {
                continue;
            }
            String encrypted = getOperatedContent(String.valueOf(value));
            map.put(targetField, encrypted);
        }
    }

    /**
     * 获取加解密后的内容，由子类完成。
     *
     * @param content 需要加解密的内容
     * @return 加解密后的内容
     */
    protected abstract String getOperatedContent(String content);
}
