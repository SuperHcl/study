package com.example.springbootmybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hu.ChangLiang
 * @since 2023/6/28 11:34
 */
@Getter
@Setter
@ToString
public class WhereMapping {
    /*
     * SELECT * FROM user u WHERE u.loginId=? and u.DELETED = 'N'
     * name = loginId
     * tableAlias = u
     */
    // where条件后条件字段名
    private String name;
    // 字段对应表的别名
    private String tableAlias;
}
