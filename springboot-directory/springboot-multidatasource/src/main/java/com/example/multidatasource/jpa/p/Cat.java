package com.example.multidatasource.jpa.p;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author hucl
 * @date 2021/9/22 5:22 下午
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cat {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer age;

    public Cat(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
