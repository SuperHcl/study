package com.shardingshpere.controller;

import com.shardingshpere.domain.dto.UserDTO;
import com.shardingshpere.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/23 15:14
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/generateUser")
    public void generateUser() {
        for (int i = 0; i < 4; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setAge(i);
            userDTO.setName("Marry" + i);
            int save = userService.save(userDTO);
            System.out.println(save);
        }
    }
}
