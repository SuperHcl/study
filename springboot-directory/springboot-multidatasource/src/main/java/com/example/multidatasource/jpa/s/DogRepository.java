package com.example.multidatasource.jpa.s;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hucl
 * @date 2021/9/22 5:24 下午
 */
public interface DogRepository extends JpaRepository<Dog, Long> {

}
