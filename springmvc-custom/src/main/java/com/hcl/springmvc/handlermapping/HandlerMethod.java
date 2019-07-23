package com.hcl.springmvc.handlermapping;

import java.lang.reflect.Method;

/**
 * @author: Hucl
 * @date: 2019/7/23 11:24
 * @description:
 */
public class HandlerMethod {
    private Method method;

    // 通过反射调用方法的是Controller对象
    private Object handler;

    public HandlerMethod(Method method, Object handler) {
        this.method = method;
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }
}
