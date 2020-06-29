package com.hcl.example.controller;

import com.hcl.example.bean.User;
import com.hcl.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hucl
 * @date 2020/6/17 18:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public User saveHandler(@RequestBody User user) {
        User res = userService.saveUser(user);
        System.out.println(res.toString());
        return res;
    }

    @DeleteMapping(value = "/del/{id}")
    public boolean deleteHandler(@PathVariable("id") int id) {
        return userService.deleteUserById(id);
    }

    @PostMapping(value = "/update")
    public User updateHandler(@RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/get/id/{id}")
    public User getByIdHandler(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/get/name/{name}")
    public List<User> getByNameHandler(@PathVariable("name") String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/list")
    public List<User> listHandler() {
        return userService.getAll();
    }

}
