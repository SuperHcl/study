package com.supergo.user.service;

import com.supergo.service.base.BaseService;
import com.supergo.common.pojo.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author wesker
 * @Date 6/6/2019 3:48 PM
 * @Version 1.0
 **/
public interface UserService extends BaseService<User> {

    Boolean verifyCode(String phone, String code);


    void sendCode(String phone);

}
