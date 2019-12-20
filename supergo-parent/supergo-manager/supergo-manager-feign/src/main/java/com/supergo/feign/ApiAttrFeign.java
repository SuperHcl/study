package com.supergo.feign;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.page.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiAttrFeign
 * @Description TODO
 * @Author wesker
 * @Date 7/15/2019 12:08 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
@RequestMapping("/attr")
public interface ApiAttrFeign {

    @RequestMapping("/queryById")
    TbAttribute findAttrById(@RequestParam("id") Integer id);

    @RequestMapping("/query")
    PageResult findAllAttr(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows);

    @RequestMapping("/save")
    Integer insertAttr(@RequestBody TbAttribute attr);

    @RequestMapping("/modify")
    Integer updateAttr(@RequestBody TbAttribute attr);

}
