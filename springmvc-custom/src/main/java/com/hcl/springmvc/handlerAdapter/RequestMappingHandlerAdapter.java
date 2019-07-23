package com.hcl.springmvc.handlerAdapter;

import com.hcl.springmvc.handlerAdapter.iface.HandlerAdapter;
import com.hcl.springmvc.handlermapping.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Hucl
 * @date: 2019/7/23 11:19
 * @description:
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HandlerMethod);
    }

    @Override
    public void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}
