package com.hcl.springmvc.handler.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:27
 * @description:
 */
public interface SimpleControllerHandler {
    Map<String, Object> handlerRequset(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
