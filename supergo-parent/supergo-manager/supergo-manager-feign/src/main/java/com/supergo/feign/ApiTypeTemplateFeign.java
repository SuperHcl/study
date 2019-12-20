package com.supergo.feign;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.common.pojo.TbTypeTemplate;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiTypeTemplateFeign {

    @RequestMapping("/template/findSpecOptionsList")
    public HttpResult findSpecOptionsList(@RequestParam("typeId") Long typeId);

    @RequestMapping("/template/getById")
    public HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/template/findAll")
    public HttpResult findAll();

    @RequestMapping("/template/delete")
    public HttpResult delete(@RequestBody(required=false) Long[] ids);

    @RequestMapping("/template/findByWhere")
    public HttpResult findByWhere(@RequestBody(required=false) TbTypeTemplate tbTypeTemplate);

    @RequestMapping("/template/add")
    HttpResult add(@RequestBody(required=false) TbTypeTemplate typeTemplate);

    @RequestMapping("/template/update")
    HttpResult update(@RequestBody(required=false) TbTypeTemplate typeTemplate);

    @RequestMapping("/template/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required=false) TbTypeTemplate typeTemplate);

    @RequestMapping("/template/queryAllAttrNotPage")
    List<TbAttribute> getAllAttrNotPage();

}
