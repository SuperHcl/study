package com.hcl.springmvc.servlet;

import com.hcl.spring.factory.BeanFactory;
import com.hcl.spring.factory.DefaultListableBeanFactory;
import com.hcl.springmvc.handlerAdapter.HttpRequestHandlerAdapter;
import com.hcl.springmvc.handlerAdapter.iface.HandlerAdapter;
import com.hcl.springmvc.handlermapping.BeanNameUrlHandlerMapping;
import com.hcl.springmvc.handlermapping.SimpleHandlerMapping;
import com.hcl.springmvc.handlermapping.iface.HandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Hucl
 * @date: 2019/7/23 09:59
 * @description: 请求分发器
 */
public class DispatcherServlet extends AbstractHttpServlet{

    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        String contextConfiguration = config.getInitParameter("contextConfigLocation");
        // 创建spring的ioc容器
        BeanFactory beanFactory = new DefaultListableBeanFactory(contextConfiguration);

        // 一次性创建所有的ioc容器中的bean
        beanFactory.getBeansByType(Object.class);
        // 根据类型获取指定的bean，放入策略集合中。
        handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);
        handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
//
//        handlerMappings.add(new SimpleHandlerMapping());
//        handlerMappings.add(new BeanNameUrlHandlerMapping());
//        handlerAdapters.add(new HttpRequestHandlerAdapter());
    }

    @Override
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取请求 URI:/UserDelete
        // 2.根据请求去匹配对应的处理器类
        Object handler = getHandler(request);
        // 3.执行处理器的方法，去处理请求（此时可以将请求进行响应）
        // 可以使用策略模式去选择合适的适配器去适配和执行处理器
        // 使用适配器模式将不同类型的类，适配为同一的类型
        // HandlerAdapter.handleRequest();

        // 3.1 根据Handler，去获取对应的HandlerAdapter()
        // handler就是笔记本电脑
        // handlerAdapter就是电脑电源适配器
        HandlerAdapter ha = getHandlerAdapter(handler);
        if (ha == null) {
            return;
        }

        try {
            //
            ha.handleRequest(handler, request, response);
//            if (handler instanceof UserDeleteHandler) {
//                ((UserDeleteHandler) handler).handleRequest1(request, response);
//            } else if (handler instanceof UserUpdateHandler) {
//                ((UserUpdateHandler) handler).handleRequest2(request, response);
//            } else if (handler instanceof HttpRequestHandler) {
//                ((HttpRequestHandler) handler).handleRequest(request, response);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        // HandlerAdapter也是有很多策略的，需要寻找合适的策略进行适配
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }
}
