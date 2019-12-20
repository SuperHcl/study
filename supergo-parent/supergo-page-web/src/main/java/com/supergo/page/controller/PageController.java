package com.supergo.page.controller;

import com.supergo.feign.ApiItemPageFeign;
import com.supergo.http.HttpResult;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item/page")
public class PageController {

    @Autowired
    private ApiItemPageFeign apiItemPageFeign;

    @RequestMapping("/findDynamicPage/{goodsId}")
    public HttpResult getDynamicPage(@PathVariable("goodsId") Long goodsId){
        return HttpResult.ok(apiItemPageFeign.getDynamicPage(goodsId));
    }
}
