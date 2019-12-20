package com.supergo.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by on 2019/5/16.
 */
@RestController
public class Auth1Controller {

    //
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/hello")
    public String showHello(){

        return "hello";
    }


    // sso测试接口
    @GetMapping("/order/{name}")
    public String getUser(@PathVariable String name) {
        System.out.println("用户名：======================="+name);
       // UserJwt jwt = ssoFeignClients.userInfo();
        return "测试:"+name;

    }

}
