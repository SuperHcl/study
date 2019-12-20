package com.supergo.test.service.impl;


import com.supergo.test.mapper.UserMapper;
import com.supergo.test.pojo.User;
import com.supergo.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





/**
 * 功能描述：用户service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 17:05
*/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    //注入
    @Autowired
    private UserMapper userMapper;

    /**
     * 添加数据
     * @return
     */
    public int insert() {

        //创建User对象
        User user1 = new User();
        user1.setUsername("张三丰8");
        userMapper.insert(user1);

        int i = 8/0;

        //创建User对象
        User user2 = new User();
        user2.setUsername("张飞1");
        userMapper.insert(user2);

        return 1;
    }


}
