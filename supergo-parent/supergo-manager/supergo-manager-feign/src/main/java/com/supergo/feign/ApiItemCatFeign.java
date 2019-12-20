package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.TbItemCat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiItemCatFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/3/2019 5:09 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiItemCatFeign {

    @RequestMapping("/itemCat/all")
    HttpResult all();

    @RequestMapping("/itemCat/parent")
    HttpResult getByParentId(@RequestParam("parentId") Long parentId);

    @RequestMapping("/itemCat/add")
    HttpResult add(@RequestBody(required = false) TbItemCat itemCat);

    @RequestMapping("/itemCat/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/itemCat/update")
    HttpResult update(@RequestBody(required = false) TbItemCat itemCat);

    @RequestMapping("/itemCat/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/itemCat/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) TbItemCat itemCat);

    @RequestMapping("/itemCat/getByParentIdAndPage")
    HttpResult getByParentIdAndPage(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, @RequestParam("id") Long id);

    @RequestMapping("/itemCat/categorysList")
    HttpResult categorysList();

}
