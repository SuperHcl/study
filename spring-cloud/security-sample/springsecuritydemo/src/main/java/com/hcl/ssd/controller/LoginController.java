package com.hcl.ssd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
}
