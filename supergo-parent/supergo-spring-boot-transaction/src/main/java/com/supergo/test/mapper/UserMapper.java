package com.supergo.test.mapper;

import com.supergo.test.pojo.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * 功能描述：UserMapper
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 17:04
*/
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
}
