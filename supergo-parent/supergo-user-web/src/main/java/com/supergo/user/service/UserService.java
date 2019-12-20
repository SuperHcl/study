package com.supergo.user.service;


import com.supergo.common.pojo.User;
import com.supergo.http.HttpResult;

/**
 * Created by on 2019/5/29.
 */
public interface UserService {

    public User create(String username, String password);
    // 查询数据是否可用
    HttpResult dataCheck(String param, Integer type);
    //用戶登錄
    HttpResult login(String username, String password);

    //根据用户token查询用户
    User userCheck(String token);

    /**
     * 测试数据
     */
    User testUser(Long userId);
}
