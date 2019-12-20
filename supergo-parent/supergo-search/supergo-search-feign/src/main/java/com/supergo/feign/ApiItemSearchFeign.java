package com.supergo.feign;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 索引服务
 *
 * @author think
 */
@FeignClient("supergo-search")
@RequestMapping("/search/item")
public interface ApiItemSearchFeign {

    @RequestMapping("/getCommonData")
    String getCommonData(@RequestParam("typeId") Integer typeId);

    @RequestMapping("/importDataToES")
    void importDataToES();

    @RequestMapping("/suggest")
    List<String> suggest(@RequestParam("index") String index, @RequestParam("suggest") String suggest);

    @RequestMapping("/searchAssociation")
    Set<String> searchAssociation(@RequestParam("inputText") String inputText);

    @RequestMapping("/searchAll")
    Map searchAll(@RequestBody Map searchMap);

}
