package com.supergo.test.controller;

import com.supergo.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：用户
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 17:04
*/
@RestController
@RequestMapping("/user")
public class UserController {

   /**
    * 功能描述：注入用户service
    * @Param
    * @Return
    * @Author jackhu
    * @Date 2019/7/24
    * @Time 17:04
   */
    @Autowired
    private UserService userService;

    /**
     * 需求：添加用户
     */
    @RequestMapping("atomitoks")
    public String showUser(){
        int insert = userService.insert();
        return "分布式事务："+insert;
    }



}
