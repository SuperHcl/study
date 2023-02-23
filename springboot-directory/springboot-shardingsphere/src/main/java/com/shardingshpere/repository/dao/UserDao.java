package com.shardingshpere.repository.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/23 15:08
 */
@Getter
@Setter
@TableName("user")
public class UserDao {
    @TableId
    private Long id;

    private Integer age;

    private String name;

}
