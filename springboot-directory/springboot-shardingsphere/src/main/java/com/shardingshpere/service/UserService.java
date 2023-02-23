package com.shardingshpere.service;

import com.shardingshpere.domain.dto.UserDTO;
import com.shardingshpere.repository.dao.UserDao;
import com.shardingshpere.repository.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * @author Hu.ChangLiang
 * @date 2023/2/23 15:14
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserMapper userMapper;


    public int save(UserDTO userDTO) {
        return userMapper.insert(userDTO.toDao());
    }

    public UserDao getById(int id) {
        return userMapper.selectById(id);
    }
}
