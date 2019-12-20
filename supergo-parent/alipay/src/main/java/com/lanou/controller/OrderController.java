package com.lanou.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.lanou.model.Orders;
import com.lanou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/alipay.do")
    @ResponseBody
    public Map alipay(String orderNo){
        Map maps = new HashMap();
        Orders orders = orderService.selectByOrderNo(orderNo);

        maps.put("orders",orders);
        return maps;
    }
    @RequestMapping("/alipayCallback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest req){
        System.out.println("支付宝调用我了！！！！！！");
        Map param = req.getParameterMap();
        System.out.println(param);
        Map resultMap = new HashMap();
        for (Iterator iterator = param.keySet().iterator();iterator.hasNext();) {
            String key = (String) iterator.next();
            String[] values = (String[]) param.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length-1)? valueStr + values[i]: valueStr + values[i] + ",";
            }
            resultMap.put(key,valueStr);
        }
        resultMap.remove("sign_type");
        System.out.println(resultMap);
        boolean res = false;
        try {
            res = AlipaySignature.rsaCheckV2(resultMap, Configs.getAlipayPublicKey(),
                    "UTF-8",Configs.getSignType());
            if(!res){
               return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //todo 各种验证和逻辑处理

        return "success";
    }

}
