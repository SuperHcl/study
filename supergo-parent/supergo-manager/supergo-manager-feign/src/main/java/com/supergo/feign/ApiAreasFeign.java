package com.supergo.feign;

import com.supergo.common.pojo.Areas;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiAreasFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/3/2019 3:12 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiAreasFeign {

    @RequestMapping("/areas/add")
    HttpResult add(@RequestBody(required = false) Areas areas);


    @RequestMapping("/areas/delete")
    HttpResult delete(@RequestBody Integer[] ids);

    @RequestMapping("/areas/update")
    HttpResult update(@RequestBody(required = false) Areas areas);

    @RequestMapping("/areas/getById")
    HttpResult getById(@RequestParam("id") Integer id);

    @RequestMapping("/areas/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Areas areas);
}
