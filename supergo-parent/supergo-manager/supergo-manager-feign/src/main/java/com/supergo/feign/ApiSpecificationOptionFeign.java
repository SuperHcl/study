package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.SpecificationOption;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiSpecificationOptionFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/4/2019 11:11 AM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiSpecificationOptionFeign {

    @RequestMapping("/specificationOption/add")
    HttpResult add(@RequestBody(required = false) SpecificationOption specificationOption);

    @RequestMapping("/specificationOption/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/specificationOption/update")
    HttpResult update(@RequestBody(required = false) SpecificationOption specificationOption);

    @RequestMapping("/specificationOption/getById")
    HttpResult getById(@RequestParam("id") Long id);


    @RequestMapping("/specificationOption/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) SpecificationOption specificationOption);
}
