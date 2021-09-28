package com.example.multidatasource.jpa.p;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author hucl
 * @date 2021/9/22 5:24 下午
 */
public interface CatRepository extends JpaRepository<Cat, Long> {

}
