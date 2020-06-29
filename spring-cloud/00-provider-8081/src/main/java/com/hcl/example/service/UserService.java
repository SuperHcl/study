package com.hcl.example.service;

import com.hcl.example.bean.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    boolean deleteUserById(int id);

    User update(User user);

    User getUserById(int id);

    List<User> getAll();

    List<User> getUserByName(String name);
}
