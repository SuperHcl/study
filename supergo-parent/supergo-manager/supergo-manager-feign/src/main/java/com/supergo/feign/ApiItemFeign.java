package com.supergo.feign;

import com.supergo.common.pojo.Cart;
import com.supergo.common.pojo.Item;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Classname ApiItemFeign
 * @Description item interface
 * @Date 2019-4-22 16:01
 * @Created by HardyLi
 */
@FeignClient("supergo-manager")
public interface ApiItemFeign {

    @RequestMapping("/item/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/item/add")
    HttpResult add(@RequestBody(required = false) Item item);

    @RequestMapping("/item/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/item/update")
    HttpResult update(@RequestBody(required = false) Item item);

    @RequestMapping("/item/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Item item);

    @RequestMapping("/item/updateStatus")
    HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status);

    @RequestMapping("/item/deductionStock")
    public boolean deductionStock(List<Cart> cartList);

}
