package com.hcl.springmvc.handlerAdapter;

import com.hcl.springmvc.handler.iface.HttpRequestHandler;
import com.hcl.springmvc.handlerAdapter.iface.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Hucl
 * @date: 2019/7/23 11:27
 * @description:
 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof HttpRequestHandler);
    }

    @Override
    public void handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 执行处理器（适配模式中的适配者类）
        ((HttpRequestHandler) handler).handleRequest(request, response);
    }
}
