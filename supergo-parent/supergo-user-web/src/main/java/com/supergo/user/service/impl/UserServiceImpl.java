package com.supergo.user.service.impl;


import com.supergo.common.pojo.User;
import com.supergo.http.HttpResult;
import com.supergo.http.HttpStatus;
import com.supergo.user.mapper.UserMapper;
import com.supergo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by on 2019/5/29.
 */
@Service
public class UserServiceImpl implements UserService {

    //注入mapper对象
    @Autowired
    private UserMapper userMapper;

    //注入redis
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用戶註冊
     * @param username
     * @param password
     * @return
     */
    @Override
    public User create(String username, String password) {

        //创建对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        //保存
        userMapper.insertSelective(user);

        return user;
    }

    // 查询数据是否可用
    @Override
    public HttpResult dataCheck(String param, Integer type) {

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        //判断参数类型
        //type==1,param 就是 Username
        if(type==1){
            criteria.andEqualTo("username",param);
        }else if(type==2){
        //type==2,param 就是 phone
            criteria.andEqualTo("phone",param);
        }else if(type==3){
        //type==3,param 就是 email
            criteria.andEqualTo("email",param);
        }
        List<User> userList = userMapper.selectByExample(example);
        //如果 list 为空，表示此用户名可用户
        if(userList==null || userList.isEmpty()){
            return HttpResult.ok(true);
        }
        //否则数据已经被占用
        return HttpResult.ok(false);

    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public HttpResult login(String username, String password) {


        //根据用户名查询数据，判断此用户是否存在
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //设置查询参数
        criteria.andEqualTo("username",username);

        //查询
        List<User> userList = userMapper.selectByExample(example);

        //如果list集合为空,表示此用户不存在
        if(userList==null || userList.size()==0){
            return HttpResult.error(HttpStatus.SC_FORBIDDEN,"用户名或密码错误");
        }

        //获取查询用户身份信息
        User user = userList.get(0);

        //判断密码是否正确
        //对密码进行md5加密
        String md5 =
                DigestUtils.md5DigestAsHex(password.getBytes());
        //判断密码是否相等
        if(!md5.equals(user.getPassword())){
            return HttpResult.error(HttpStatus.SC_FORBIDDEN,"用户名或密码错误");
        }
        //返回Token，UUID，自己生成UUID，作为Token
        String token = UUID.randomUUID().toString();
        //把用户身份信息写入redis
        //把密码置null
        user.setPassword(null);
        //把用户信息存储在redis
        redisTemplate.boundValueOps("SESSION_KEY:"+token).set(user);

        //设置过期时间
        redisTemplate.boundValueOps("SESSION_KEY:"+token).expire(30, TimeUnit.MINUTES);
        //返回Token
        return HttpResult.ok(token);

    }

    /**
     * 查询用户信息
     * @param token
     * @return
     */
    @Override
    public User userCheck(String token) {

        //根据Token查询redis服务器，查询用户身份信息
        Object o = redisTemplate.boundValueOps("SESSION_KEY:" + token).get();

        o = new User();

        //判断
        if(o!=null){
            //把json字符串转换成tbUser对象
            User user = (User) o;
            //重置redis中用户身份认证过去时间
            //redisTemplate.boundValueOps("SESSION_KEY:"+token).expire(30, TimeUnit.MINUTES);

            user.setId(15L);
            user.setUsername("张三丰");
            user.setPhone("1357878987");

            return user;
        }
        //如果redis服务器没有用户身份认证，表示此用户已经过期
        return null;

    }

    /**
     * 测试数据
     */
    @Override
    public User testUser(Long userId) {

        //注入mapper
        User user = userMapper.selectByPrimaryKey(userId);

        return user;
    }
}
