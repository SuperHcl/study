package com.example.springbootmybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Hu.ChangLiang
 * @date 2023/5/31 11:30
 */
@Getter
@Setter
@ToString
public class ColMapping {
    /** 字段名 */
    private String name;
    /** 字段别名 */
    private String alias;
    /** 关联表 */
    private Object table;
    private String type;
}
