package com.supergo.manager.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 登录相关操作
 * @Author  jackhu
 * @Date 9/26/2019 10:34 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/login")
@Api(value = "登录相关操作", protocols = "http", description = "登录相关操作")
public class LoginController {

    /**
     * @Description 
     * @Author  jackhu
     * @Date 9/26/2019 10:35 AM
     * @Param []
     * @Return java.lang.String
     * @Exception   
     */
    @RequestMapping(value = "/name")
    public String name(){
        return "zhangsan";
    }
}
