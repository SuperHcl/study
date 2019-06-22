package com.hcl.rpc.api.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:24
 * @description:
 */
@Data
public class Product implements Serializable {

    private Integer id;

    private String name;

    private Double price;
}
