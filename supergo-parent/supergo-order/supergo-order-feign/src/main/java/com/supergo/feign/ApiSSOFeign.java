package com.supergo.feign;

import com.supergo.common.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by on 2019/10/25.
 */
@FeignClient("supergo-user-web")
public interface ApiSSOFeign {

    @RequestMapping("/user/token/{token}")
    User getUserByToken(@PathVariable("token") String token);

}
