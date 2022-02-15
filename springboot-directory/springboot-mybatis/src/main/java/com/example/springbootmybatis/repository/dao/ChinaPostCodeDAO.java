package com.example.springbootmybatis.repository.dao;

import lombok.Data;

/**
 * @author Hu.ChangLiang
 * @date 2022/1/17 9:29 下午
 */
@Data
public class ChinaPostCodeDAO {
    private String code;

    private String name;

    private String type;

    private String parentCode;
}
