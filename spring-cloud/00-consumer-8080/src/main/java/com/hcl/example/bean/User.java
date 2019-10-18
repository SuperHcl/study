package com.hcl.example.bean;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author: Hucl
 * @date: 2019/10/18 15:26
 * @description:
 */
@Data
@Accessors(chain = true)
public class User {

    private String name;

    private Integer age;
}
