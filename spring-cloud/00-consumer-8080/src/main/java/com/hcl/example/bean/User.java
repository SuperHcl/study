package com.hcl.example.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author: Hucl
 * @date: 2019/10/18 15:26
 * @description:
 */
@ToString
@Setter
@Getter
@Accessors(chain = true)
public class User implements Serializable {

    private int id;
    private String name;
    private String address;
    private Integer age;
    private String email;
}
