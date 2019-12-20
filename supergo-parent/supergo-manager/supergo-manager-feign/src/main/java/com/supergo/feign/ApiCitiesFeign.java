package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Cities;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiCitiesFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/3/2019 3:26 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiCitiesFeign {


    @RequestMapping("/cities/add")
    HttpResult add(@RequestBody(required = false) Cities cities);

    @RequestMapping("/cities/delete")
    HttpResult delete(@RequestBody Integer[] ids);

    @RequestMapping("/cities/update")
    HttpResult update(@RequestBody(required = false) Cities cities);

    @RequestMapping("/cities/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/cities/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Cities cities);
}
