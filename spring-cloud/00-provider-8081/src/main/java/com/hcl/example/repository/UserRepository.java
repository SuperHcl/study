package com.hcl.example.repository;


import com.hcl.example.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);
}
