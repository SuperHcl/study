package com.supergo.auth.service;
import com.supergo.common.pojo.User;
import com.supergo.auth.domain.UserJwt;
import com.supergo.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //注入mapper接口
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /*

        String finalPassword = "{bcrypt}"+new BCryptPasswordEncoder().encode("123456");
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(username,finalPassword,authorities);

        */
        //创建example对象
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        List<User> users = userMapper.selectByExample(example);

        User user = null;

        //判断
        if(users!=null && users.size()>0){
            user = users.get(0);
        }

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        //创建userJwt对象
        UserJwt userJwt = new UserJwt(username,user.getPassword(),authorities);
        //设置用户数据
        userJwt.setId(user.getId());
        userJwt.setUsername(user.getUsername());

        return userJwt;

    }
}
