package com.supergo.page.controller;

import com.supergo.http.HttpResult;
import com.supergo.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName ItemPageController
 * @Description TODO
 * @Author wesker
 * @Date 7/12/2019 1:38 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/item/page")
public class ItemPageController {

    @Autowired
    private ItemPageService itemPageService;

    @RequestMapping("/buildHtml")
    public HttpResult buildHtml(@RequestParam("goodsId") Long goodsId) {
        return HttpResult.ok(itemPageService.buildHtml(goodsId));
    }

    @RequestMapping("/deleteByGoodsId")
    public HttpResult deleteByGoodsId(@RequestParam("id") Long id) {
        try {
            itemPageService.deleteByGoodsId(id);
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.error();
        }
    }

    @RequestMapping("/getDynamicPage")
    public Map<String,Object> getDynamicPage(@RequestParam("goodsId") Long goodsId){
        return itemPageService.getDynamicPage(goodsId);
    }
}
