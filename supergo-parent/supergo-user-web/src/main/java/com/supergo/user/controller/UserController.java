package com.supergo.user.controller;

import com.supergo.common.pojo.User;
import com.supergo.http.HttpResult;
import com.supergo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by on 2019/5/29.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //注入service
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public User createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
            return userService.create(username, password);

    }


    //查询输入参数是否可用
    // 查询数据是否可用
    @RequestMapping("/check/{param}/{type}")
    public HttpResult dataCheck(@PathVariable String param,
                                @PathVariable Integer type) {
        HttpResult dataCheck = userService.dataCheck(param,
                type);
        return dataCheck;
    }


    //单点登录
    @RequestMapping("/login")
    public HttpResult login(String username, String password) {

        HttpResult result = userService.login(username,
                password);

        return result;
    }



    //获取用户信息
    //首页跨域获取用户身份信息
    @RequestMapping("/token/{token}")
    public User userCheck(@PathVariable String token){
        //根据Token查询redis服务器用户身份信息
        User user = userService.userCheck(token);
        return user;
    }

    /**
     * 测试数据
     */
    @RequestMapping("/test/{userId}")
    public User testData(@PathVariable Long userId){
       User user = userService.testUser(userId);

       return user;
    }

}
