package com.supergo.feign;

import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("supergo-pay")
public interface ApiWeixinPayFeign {

    @RequestMapping("/pay/closePay/{outtradeno}")
    HttpResult closePay(@RequestParam("outtradeno") String outtradeno);

    @RequestMapping("/pay/queryStatus/{outtradeno}")
    HttpResult queryStatus(@RequestParam("outtradeno") String outtradeno);

    @RequestMapping("/pay/createNative/{outtradeno}/{money}")
    HttpResult createNative(@RequestParam("outtradeno") String outtradeno, @RequestParam("money") String money);
}
