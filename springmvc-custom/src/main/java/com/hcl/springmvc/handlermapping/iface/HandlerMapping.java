package com.hcl.springmvc.handlermapping.iface;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Hucl
 * @date: 2019/7/23 10:27
 * @description:
 */
public interface HandlerMapping {

    /**
     *  根据请求查找处理器
     * @param request request
     * @return {@link com.hcl.springmvc.handler}
     */
    Object getHandler(HttpServletRequest request);
}
