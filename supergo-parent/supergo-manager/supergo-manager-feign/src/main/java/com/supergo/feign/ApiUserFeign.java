package com.supergo.feign;

import com.supergo.common.pojo.User;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiUserFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/4/2019 11:43 AM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiUserFeign {

    @RequestMapping("/user/add")
    HttpResult add(@RequestBody(required = false) User user);


    @RequestMapping("/user/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/user/update")
    HttpResult update(@RequestBody(required = false) User user);


    @RequestMapping("/user/getById")
    HttpResult getById(@RequestParam("id") Long id);


    @RequestMapping("/user/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) User user);
}
