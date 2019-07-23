package com.hcl.springmvc.handlermapping;

import com.hcl.springmvc.handler.UserDeleteHandler;
import com.hcl.springmvc.handler.UserQueryHandler;
import com.hcl.springmvc.handler.UserUpdateHandler;
import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/23 10:26
 * @description: 简单handler mapping映射器
 */
public class SimpleHandlerMapping implements HandlerMapping {

    private Map<String, Object> urlHandlerMap = new HashMap<>();

    public SimpleHandlerMapping() {
        urlHandlerMap.put("/userDelete", new UserDeleteHandler());
        urlHandlerMap.put("/userUpdate", new UserUpdateHandler());
        urlHandlerMap.put("/userQuery", new UserQueryHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return urlHandlerMap.get(uri);
    }
}
