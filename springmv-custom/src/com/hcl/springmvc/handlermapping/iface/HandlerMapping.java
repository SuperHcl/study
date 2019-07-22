package com.hcl.springmvc.handlermapping.iface;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Hucl
 * @date: 2019/7/22 16:14
 * @description:
 */
public interface HandlerMapping {
    Object getHandler(HttpServletRequest request);
}
