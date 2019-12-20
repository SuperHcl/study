package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.SeckillOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("supergo-manager")
public interface ApiSeckillOrderFeign {


    @RequestMapping("/seckillOrder/add")
    HttpResult add(@RequestBody(required = false) SeckillOrder seckillOrder);

    @RequestMapping("/seckillOrder/delete")
    HttpResult delete(@RequestBody Long[] ids);

    @RequestMapping("/seckillOrder/update")
    HttpResult update(@RequestBody(required = false) SeckillOrder seckillOrder);


    @RequestMapping("/seckillOrder/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/seckillOrder/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) SeckillOrder seckillOrder);

}
