package com.hcl.mybatis.pojo;

import lombok.Data;

/**
 * @author: Hucl
 * @date: 2019/6/29 16:33
 * @description:
 */
@Data
public class User {

    private Integer id;

    private String name;

    private String address;

    private Integer age;

    private String email;
}
