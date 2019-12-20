package com.supergo.gateway.filter;/*
package com.jmyp.gateway.filter;

imports com.alibaba.fastjson.JSON;
imports HttpResult;
imports com.netflix.zuul.ZuulFilter;
imports com.netflix.zuul.context.RequestContext;
imports com.netflix.zuul.exception.ZuulException;
imports org.apache.commons.lang3.StringUtils;

imports javax.servlet.http.HttpServletRequest;
imports javax.servlet.http.HttpServletResponse;

*/
/**
 * @author Administrator
 * @version 1.0
 **//*


//@Component
public class LoginFilterTest extends ZuulFilter {

    //过虑器的类型
    @Override
    public String filterType() {
        */
/**
         pre：请求在被路由之前执行

         routing：在路由请求时调用

         post：在routing和errror过滤器之后调用

         error：处理请求时发生错误调用

         *//*

        return "pre";
    }

    //过虑器序号，越小越被优先执行
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //返回true表示要执行此过虑器
        return true;
    }

    //过虑器的内容
    //测试的需求：过虑所有请求，判断头部信息是否有Authorization，如果没有则拒绝访问，否则转发到微服务。
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        //得到request
        HttpServletRequest request = requestContext.getRequest();
        //得到response
        HttpServletResponse response = requestContext.getResponse();
        //得到Authorization头
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            //拒绝访问
            requestContext.setSendZuulResponse(false);
            //设置响应代码
            requestContext.setResponseStatusCode(200);
            //构建响应的信息

            //转成json
            String jsonString = JSON.toJSONString( HttpResult.error(400,"此操作需要登陆系统"));
            requestContext.setResponseBody(jsonString);
            //转成json，设置contentType
            response.setContentType("application/json;charset=utf-8");
            return null;
        }

        return null;
    }
}
*/
