package com.hcl.springmvc.handlermapping;

import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:16
 * @description:
 */
public class BeanUrlHandlerMapping implements HandlerMapping {
    private Map<String, Object> urlHandlerMap = new HashMap<>();

    public BeanUrlHandlerMapping() {
        // 读取spring容器，获取bean的信息，该bean并且的name属性就是url，该bean对象就是该url对应的对象
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return urlHandlerMap.get(uri);
    }
}
