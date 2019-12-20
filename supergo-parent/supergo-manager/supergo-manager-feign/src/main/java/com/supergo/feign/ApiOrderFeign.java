package com.supergo.feign;


import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("supergo-manager")
public interface ApiOrderFeign {


    @RequestMapping("/order/add")
    HttpResult add(@RequestBody(required = false) Order order);


    @RequestMapping("/order/delete")
    HttpResult delete(@RequestBody Long[] ids);


    @RequestMapping("/order/update")
    HttpResult update(@RequestBody(required = false) Order order);

    @RequestMapping("/order/getById")
    HttpResult getById(@RequestParam("id") Long id);


    @RequestMapping("/order/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Order order);
}
