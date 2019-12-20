package com.supergo.feign;

import com.supergo.common.pojo.Provinces;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("supergo-manager")
public interface ApiProvincesFeign {


    @RequestMapping("/provinces/add")
    HttpResult add(@RequestBody(required = false) Provinces provinces);


    @RequestMapping("/provinces/delete")
    HttpResult delete(@RequestBody Integer[] ids);

    @RequestMapping("/provinces/update")
    HttpResult update(@RequestBody(required = false) Provinces provinces);

    @RequestMapping("/provinces/getById")
    HttpResult getById(@RequestParam("id") Integer id);

    @RequestMapping("/provinces/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Provinces provinces);

}
