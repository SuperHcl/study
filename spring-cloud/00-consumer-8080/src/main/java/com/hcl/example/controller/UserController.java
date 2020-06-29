package com.hcl.example.controller;

import com.hcl.example.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author hucl
 * @date 2020/6/17 22:50
 */
@RestController
@RequestMapping(value = "/consumer")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;
    private static final String PROVIDER_SERVER = "http://hcl-provider-8081/provider/user";

    @PostMapping(value = "/save")
    public User save(@RequestBody User user) {
        String url = PROVIDER_SERVER + "/add";
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(url, user, User.class);
        return userResponseEntity.getBody();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        String url = PROVIDER_SERVER + "/del/"+id;
        restTemplate.delete(url);
    }

    @PostMapping("/update")
    public User update(@RequestBody User user) {
        String url = PROVIDER_SERVER + "/update";
        return restTemplate.postForObject(url, user, User.class);
    }

    @GetMapping("/id/{id}")
    public User getById(@PathVariable("id") int id) {
        String url = PROVIDER_SERVER + "/get/id/"+id;
        return restTemplate.getForObject(url, User.class);
    }

    @GetMapping("/name/{name}")
    public User getByName(@PathVariable("name") String name) {
        String url = PROVIDER_SERVER + "/get/name/"+name;
        return restTemplate.getForObject(url, User.class);
    }

    @GetMapping("/all")
    public List<User> getAll() {
        String url = PROVIDER_SERVER + "/list";
        return restTemplate.getForObject(url, List.class);
    }


}
