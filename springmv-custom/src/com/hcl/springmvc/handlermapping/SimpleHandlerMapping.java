package com.hcl.springmvc.handlermapping;

import com.hcl.springmvc.handler.UserDeleteHandler;
import com.hcl.springmvc.handler.UserUpdateHandler;
import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:15
 * @description:
 */
public class SimpleHandlerMapping implements HandlerMapping {
    private Map<String, Object> urlHandlerMap = new HashMap<>();

    public SimpleHandlerMapping() {
        urlHandlerMap.put("/userDelete", new UserDeleteHandler());
        urlHandlerMap.put("/userUpdate", new UserUpdateHandler());
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return urlHandlerMap.get(uri);
    }
}
