package com.supergo.feign;

import com.supergo.common.pojo.FreightTemplate;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiFreightTemplateFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/3/2019 4:43 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiFreightTemplateFeign {

    @RequestMapping("/freightTemplate/add")
    HttpResult add(@RequestBody(required = false) FreightTemplate freightTemplate);

    @RequestMapping("/freightTemplate/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/freightTemplate/update")
    HttpResult update(@RequestBody(required = false) FreightTemplate freightTemplate);

    @RequestMapping("/freightTemplate/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/freightTemplate/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) FreightTemplate freightTemplate);

}
