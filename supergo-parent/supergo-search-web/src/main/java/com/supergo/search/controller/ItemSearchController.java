package com.supergo.search.controller;


import com.supergo.feign.ApiItemSearchFeign;
import com.supergo.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/12 18:02
 *
 ****/
@RestController
@RequestMapping(value = "/search/item")
public class ItemSearchController {

    @Autowired
    private ApiItemSearchFeign apiItemSearchFeign;

    // 导入索引
    @RequestMapping("/dbDataToES")
    public HttpResult importDataToES() {
        apiItemSearchFeign.importDataToES();
        return HttpResult.ok();
    }

    // 搜索
    @RequestMapping("/searchConditionByEs")
    public HttpResult searchAll(@RequestBody Map searchMap){
        return HttpResult.ok(apiItemSearchFeign.searchAll(searchMap));
    }

    // 联想搜索
    @RequestMapping("/getAssociation")
    public HttpResult searchAssociation(@RequestParam("inputText") String inputText){
        return HttpResult.ok(apiItemSearchFeign.searchAssociation(inputText));
    }
}
