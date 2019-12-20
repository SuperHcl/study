package com.supergo.feign;

import com.supergo.common.pojo.Content;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiContentFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/3/2019 3:39 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiContentFeign {

    @RequestMapping("/content/add")
    HttpResult add(@RequestBody(required = false) Content content);

    @RequestMapping("/content/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/content/update")
    HttpResult update(@RequestBody(required = false) Content content);


    @RequestMapping("/content/getById")
    HttpResult getById(@RequestParam("id") Long id);


    @RequestMapping("/content/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Content content);

    @RequestMapping("/content/categoryContentList")
    HttpResult categoryContentList(@RequestParam("categoryId") Long categoryId);

}