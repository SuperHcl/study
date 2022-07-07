package com.hcl.ssd.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author Hu.ChangLiang
 * @date 2022/7/7 14:45
 */
@Service
public class MyAuthServiceImpl implements MyAuthService {

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (obj instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) obj;
            // 获取所拥有的的权限
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            // 判断当前请求的uri是否在所拥有的权限里面
            System.out.println("----" + request.getRequestURI() + "--");
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }
        return false;
    }
}
