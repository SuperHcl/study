package com.example.springbootmybatis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2023/5/31 11:30
 */
@Getter
@Setter
@ToString
public class NormalSqlStructure {
    /** SQL语句 */
    private String sql;
    /** 表名 */
    private List<String> tableNames;
    /** 检索项 */
    private List<String> selectItems;

    /** 字段和表的映射关系 */
    private List<ColMapping> colMappings;

    /** where条件的字段映射*/
    private List<WhereMapping> whereMappings;
}
