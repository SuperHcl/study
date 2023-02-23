package com.shardingshpere.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shardingshpere.repository.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/23 15:09
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDao> {
}
