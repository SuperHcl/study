package com.hcl.example.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: Hucl
 * @date: 2019/10/18 15:37
 * @description:
 */
@ToString
@Getter
@Setter
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自动递增
    private int id;
    private String name;
    private String address;
    private Integer age;
    private String email;

}
