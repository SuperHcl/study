package com.supergo.user.controller;

import com.supergo.user.service.UserService;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author wesker
 * @Date 6/6/2019 3:44 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/verifyCode")
    public HttpResult verifyCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        Boolean result = userService.verifyCode(phone, code);
        return HttpResult.ok(result);
    }


    @RequestMapping("/sendCode")
    public HttpResult sendCode(@RequestParam("phone") String phone) {
        try {
            userService.sendCode(phone);
            return HttpResult.ok("发送验证码成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.ok("发送验证码失败!");
    }

    @RequestMapping("/user/add")
    public HttpResult add(@RequestBody(required = false) User user){
        try {
            userService.add(user);
            return HttpResult.ok("添加用户成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加用户失败!");
    }

}
