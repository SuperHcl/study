package com.hcl.springmvc.servlet;

import com.hcl.springmvc.handler.UserDeleteHandler;
import com.hcl.springmvc.handler.UserUpdateHandler;
import com.hcl.springmvc.handler.iface.HttpRequestHandler;
import com.hcl.springmvc.handlermapping.BeanUrlHandlerMapping;
import com.hcl.springmvc.handlermapping.SimpleHandlerMapping;
import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:10
 * @description: 请求分发器
 */
public class DispatcherServlet extends AbstractHttpServlet {

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        handlerMappings.add(new SimpleHandlerMapping());
        handlerMappings.add(new BeanUrlHandlerMapping());
    }


    /**
     * 请求转发（分发给具体的处理器）
     */
    @Override
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 1.获取请求 URI:/UserDelete
            // 2.根据请求去匹配对应的处理器类
            Object handler = getHandler(request);
            // 3.执行处理器的方法，去处理请求（此时可以将请求进行响应）
            // 可以使用策略模式去选择合适的适配器去适配和执行处理器
            // 使用适配器模式将不同类型的类，适配为同一的类型
            // HandlerAdapter.handleRequest();
            if (handler instanceof UserDeleteHandler) {
                ((UserDeleteHandler) handler).handleRequest1(request, response);
            } else if (handler instanceof UserUpdateHandler) {
                ((UserUpdateHandler) handler).handleRequest2(request, response);
            } else if (handler instanceof HttpRequestHandler) {
                ((HttpRequestHandler) handler).handleRequest(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHandler(HttpServletRequest request) {
        // 处理器类无法统一成一个类型（有的使用注解，有的使用类似Servlet的实现方式）
        // 委托给另一个类去查找HandlerMapping（建立请求和处理器的映射关系、根据请求查找处理器）
        // 为什么要使用另一个类去查找处理器
        // 因为url和处理的映射关系，可能是通过map来保存的。可能是通过配置文件配置配的
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(request);
            if (handler!=null) {
                return handler;
            }
        }
        return null;
    }
}
