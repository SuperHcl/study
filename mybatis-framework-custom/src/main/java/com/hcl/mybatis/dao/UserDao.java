package com.hcl.mybatis.dao;

import com.hcl.mybatis.pojo.User;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:42
 * @description:
 */
public interface UserDao {
    User findUserById(Integer id);
}
