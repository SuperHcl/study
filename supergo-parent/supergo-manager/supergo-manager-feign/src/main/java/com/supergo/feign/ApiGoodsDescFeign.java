package com.supergo.feign;

import com.supergo.common.pojo.GoodsDesc;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName ApiGoodsDescFeign
 * @Description TODO
 * @Author wesker
 * @Date 6/3/2019 4:57 PM
 * @Version 1.0
 **/
@FeignClient("supergo-manager")
public interface ApiGoodsDescFeign {

    @RequestMapping("/goodsDesc/add")
    HttpResult add(@RequestBody(required = false) GoodsDesc goodsDesc);

    @RequestMapping("/goodsDesc/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/goodsDesc/update")
    HttpResult update(@RequestBody(required = false) GoodsDesc goodsDesc);

    @RequestMapping("/goodsDesc/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/goodsDesc/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) GoodsDesc goodsDesc);
}
