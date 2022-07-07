package com.hcl.ssd.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义权限认证服务
 *
 * @author Hu.ChangLiang
 * @date 2022/7/7 14:33
 */
public interface MyAuthService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
