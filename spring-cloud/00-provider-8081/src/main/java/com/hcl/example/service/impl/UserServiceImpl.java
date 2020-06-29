package com.hcl.example.service.impl;

import com.hcl.example.bean.User;
import com.hcl.example.repository.UserRepository;
import com.hcl.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUserById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        User res = null;
        if (userRepository.existsById(id)) {
            res = userRepository.getOne(id);
        }
        return res;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }
}
