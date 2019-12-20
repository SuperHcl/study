package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.PayLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("supergo-manager")
public interface ApiPayLogFeign {

    @RequestMapping("/payLog/add")
    HttpResult add(@RequestBody(required = false) PayLog payLog);

    @RequestMapping("/payLog/delete")
    HttpResult delete(@RequestBody String[] outTradeNos);

    @RequestMapping("/payLog/update")
    HttpResult update(@RequestBody(required = false) PayLog payLog);

    @RequestMapping("/payLog/getByOutTradeNo")
    HttpResult getById(@RequestParam("id") String id);


    @RequestMapping("/payLog/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) PayLog payLog);
}
