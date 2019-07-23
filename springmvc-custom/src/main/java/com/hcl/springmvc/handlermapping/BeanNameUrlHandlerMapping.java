package com.hcl.springmvc.handlermapping;

import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/23 10:32
 * @description: 注解形式
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping {
    private Map<String, Object> urlHandlerMap = new HashMap<>();

    public BeanNameUrlHandlerMapping() {
        // 注解形式的
    }


    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return urlHandlerMap.get(uri);
    }
}
