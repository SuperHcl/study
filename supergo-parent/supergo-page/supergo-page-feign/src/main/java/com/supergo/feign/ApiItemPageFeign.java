package com.supergo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/17 15:39
 *
 ****/
@FeignClient("supergo-page")
@RequestMapping("/item/page")
public interface ApiItemPageFeign {

    /***
     * 生成静态页
     * @param goodsId
     * @return
     */
    @RequestMapping("/buildHtml")
    boolean buildHtml(@RequestParam("goodsId") Long goodsId);

    /***
     * 根据GoodsId删除静态页
     * @param id
     */
    @RequestMapping("/deleteByGoodsId")
    void deleteByGoodsId(@RequestParam("id") Long id);


    @RequestMapping("/getDynamicPage")
    Map<String,Object> getDynamicPage(@RequestParam("goodsId") Long goodsId);
}
