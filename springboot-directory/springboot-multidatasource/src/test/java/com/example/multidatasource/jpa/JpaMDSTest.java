package com.example.multidatasource.jpa;

import com.example.multidatasource.jpa.p.CatRepository;
import com.example.multidatasource.jpa.s.Dog;
import com.example.multidatasource.jpa.s.DogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2021/9/28 10:10 上午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaMDSTest {
    @Resource
    private DogRepository dogRepository;
    @Resource
    private CatRepository catRepository;

    public void dog_test(){

    }

    @Test
    public void cat_test(){
        dogRepository.save(new Dog("豆豆", 5));
        dogRepository.save(new Dog("小白", 3));
        dogRepository.save(new Dog("小雨点", 2));

        List<Dog> all = dogRepository.findAll();

        all.forEach(System.out::println);
    }
}
