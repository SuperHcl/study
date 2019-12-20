package com.supergo.feign;

import com.supergo.common.pojo.ContentCategory;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiContentCategoryFeign {

    @RequestMapping("/categorys/all")
    HttpResult list();

    @RequestMapping("/categorys/add")
    HttpResult add(@RequestBody(required = false) ContentCategory contentCategory);

    @RequestMapping("/categorys/delete")
    HttpResult delete(@RequestBody Integer[] ids);

    @RequestMapping("/categorys/update")
    HttpResult update(@RequestBody ContentCategory contentCategory);

    @RequestMapping("/categorys/getById")
    HttpResult getById(@RequestParam("id") Integer id);

    @RequestMapping("/categorys/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) ContentCategory contentCategory);

    @RequestMapping("/categorys/categorysList")
    HttpResult categorysList();

}
