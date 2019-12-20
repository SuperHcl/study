package com.supergo.feign;

import com.supergo.common.pojo.User;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * 功能描述: 
 *
 * @param:
 * @return: 
 * @auther: jackhu
 * @date: 6/6/2019 3:35 PM
 */
@FeignClient("jmyp-user")
public interface ApiUserFeign {
    
    /**
     *
     * 功能描述: 
     *
     * @param:
     * @return: 
     * @auther: jackhu
     * @date: 6/6/2019 3:35 PM
     */
    @RequestMapping("/user/verifyCode")
    HttpResult verifyCode(@RequestParam("phone") String phone, @RequestParam("code") String code);

    /**
     *
     * 功能描述: 
     *
     * @param:
     * @return: 
     * @auther: jackhu
     * @date: 6/6/2019 3:35 PM
     */
    @RequestMapping("/user/sendCode")
    HttpResult sendCode(@RequestParam("phone") String phone);

    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: jackhu
     * @date: 6/6/2019 3:35 PM
     */
    @RequestMapping("/user/add")
    HttpResult add(@RequestBody(required = false) User user);
}
