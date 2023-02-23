package com.shardingshpere.domain.dto;

import com.shardingshpere.repository.dao.UserDao;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/23 15:19
 */
@Getter
@Setter
public class UserDTO {
    private String name;

    private Integer age;

    public UserDao toDao() {
        UserDao userDao = new UserDao();
        userDao.setAge(getAge());
        userDao.setName(getName());
        return userDao;
    }
}
