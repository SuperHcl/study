package com.hcl.ssd.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hu.ChangLiang
 * @date 2022/7/1 16:52
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsService。。。");

        // 1查询数据库判断用户名是否存在，不存在抛异常
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        // 2把查询出来的密码（已经加密过了）进行解析，或者直接把密码放入构造方法中
        String password = passwordEncoder.encode("123");
        // 返回user
        return new User(username, password,
                // ROLE_abc是角色定义，ROLE_是角色的固定前缀，一定要加
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc"));
    }
}
