package com.hcl.example.controller;

import com.hcl.example.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: Hucl
 * @date: 2019/10/18 15:06
 * @description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    public static final String SERVICE_PROVIDER = "http://hcl-provider-8081";

    @Autowired
    private RestTemplate template;

    @GetMapping("/{age}/{name}")
    public boolean testHandler(@PathVariable("age") int age, @PathVariable("name") String name) {
        User user = new User().setAge(age).setName(name);
        String url = SERVICE_PROVIDER + "/provider/save";
        return template.postForObject(url, user, Boolean.class);
    }

}
