package com.supergo.pay.controller;

import com.supergo.http.HttpResult;
import com.supergo.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 关闭订单
     * @param outtradeno
     * @return
     */
    @RequestMapping("/closePay/{outtradeno}")
    public HttpResult closePay(@RequestParam("outtradeno") String outtradeno) {
        try {
            Map<String, String> ret = payService.closePay(outtradeno);
            return HttpResult.ok(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("关闭失败");
    }

    /**
     * 查询订单状态
     * @param outtradeno
     * @return
     */
    @RequestMapping("/queryStatus/{outtradeno}")
    public HttpResult queryStatus(@RequestParam("outtradeno") String outtradeno) {
        try {
            Map<String, String> ret = payService.queryStatus(outtradeno);
            return HttpResult.ok(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("查询失败");
    }

    /**
     * 创建二维码
     * @param outtradeno
     * @param money
     * @return
     */
    @RequestMapping("/createNative/{outtradeno}/{money}")
    public HttpResult createNative(@RequestParam("outtradeno") String outtradeno, @RequestParam("money") String money) {
        try {
            Map<String, String> ret = payService.createNative(outtradeno, money);
            return HttpResult.ok(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("创建失败");
    }
}
