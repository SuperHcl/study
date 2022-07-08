package com.hcl.ssd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录控制器
 *
 * @author Hu.ChangLiang
 * @date 2022/7/1 15:15
 */
@Controller
@Slf4j
public class LoginController {

//    @Secured("ROLE_abc2")
    // PreAuthorize注解的功能就是配置类中的access
    // 有个比配置类强大的功能点是，hasRole可以ROLE_开头，也可以不用ROLE_开头，而配置中只能以ROLE_开头
    @PreAuthorize("hasRole('abc')")
    @RequestMapping("toMain")
    public String toMain() {
        log.info("...跳转登录页面....");
        return "redirect:main.html";
    }

    @RequestMapping("toError")
    public String toError() {
        log.info("...跳转失败页面....");
        return "redirect:error.html";
    }

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }
}
