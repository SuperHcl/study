package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.SeckillGoods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("supergo-manager")
public interface ApiSeckillGoodsFeign {


    @RequestMapping("/seckillGoods/update/status")
    HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status);


    @RequestMapping("/seckillGoods/add")
    HttpResult add(@RequestBody(required = false) SeckillGoods seckillGoods);


    @RequestMapping("/seckillGoods/delete")
    HttpResult delete(@RequestBody Long[] ids);


    @RequestMapping("/seckillGoods/update")
    HttpResult update(@RequestBody(required = false) SeckillGoods seckillGoods);


    @RequestMapping("/seckillGoods/getById")
    HttpResult getById(@RequestParam("id") Long id);


    @RequestMapping("/seckillGoods/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) SeckillGoods seckillGoods);
}
