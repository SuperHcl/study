package com.supergo.feign;

import com.supergo.common.pojo.Address;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiAddressFeign
 * @Description TODO
 * @Author hubin
 * @Date 6/3/2019 2:49 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiAddressFeign {

    @RequestMapping("/address/add")
    HttpResult add(@RequestBody(required = false) Address address);

    @RequestMapping("/address/delete")
    HttpResult delete(@RequestBody(required = false) Long[] ids);

    @RequestMapping("/address/update")
    HttpResult update(@RequestBody(required = false) Address address);

    @RequestMapping("/address/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/address/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Address address);
}
