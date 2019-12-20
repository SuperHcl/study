package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("supergo-manager")
public interface ApiOrderItemFeign {


    @RequestMapping("/orderItem/add")
    HttpResult add(@RequestBody(required = false) OrderItem orderItem);

    @RequestMapping("/orderItem/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/orderItem/update")
    HttpResult update(@RequestBody(required = false) OrderItem orderItem);

    @RequestMapping("/orderItem/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/orderItem/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) OrderItem orderItem);
}
