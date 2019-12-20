package com.supergo.pay.service.impl;

import com.github.wxpay.sdk.WXPayUtil;
import com.supergo.pay.service.PayService;
import com.supergo.user.utils.HttpClient;
import com.supergo.pay.config.StaticProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {

    /**
     * 订单超时，关闭订单
     * @param outtradeno
     * @return
     */
    @Override
    public Map<String, String> closePay(String outtradeno) {
        try {
            //封装要发送的参数
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("appid", StaticProperty.APPID);   //公众账号ID
            paramMap.put("mch_id",StaticProperty.PARTNER);  //商户号
            paramMap.put("out_trade_no", outtradeno);    //商户订单号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());   //随机字符串
            //将Map转成XML数据
            String xmlParam = WXPayUtil.generateSignedXml(paramMap,StaticProperty.PARTNERKEY);
            //调用关闭支付地址
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //获取返回结果
            String result = httpClient.getContent();
            Map<String,String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询订单状态
     * @param outtradeno
     * @return
     */
    @Override
    public Map<String, String> queryStatus(String outtradeno) {
        try {
            //封装要发送的参数
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("appid",StaticProperty.APPID);   //公众账号ID
            paramMap.put("mch_id",StaticProperty.PARTNER);  //商户号
            paramMap.put("out_trade_no", outtradeno);    //商户订单号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());   //随机字符串
            //将Map转成XML数据
            String xmlParam = WXPayUtil.generateSignedXml(paramMap,StaticProperty.PARTNERKEY);
            //调用查询
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //获取返回结果
            String result = httpClient.getContent();
            Map<String,String> resultMap = WXPayUtil.xmlToMap(result);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下单实现，返回支付地址
     * @param outtradeno
     * @param money
     * @return
     */
    @Override
    public Map<String, String> createNative(String outtradeno, String money) {
        try {
            //参考接口文档，添加传输参数
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("appid",StaticProperty.APPID);   //公众账号ID
            paramMap.put("mch_id",StaticProperty.PARTNER);  //商户号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());   //随机字符串
            paramMap.put("body", "超级购");        //商品描述
            paramMap.put("out_trade_no", outtradeno);    //商户订单号
            paramMap.put("total_fee",money);       //支付金额，单位：分
            paramMap.put("spbill_create_ip", "127.0.0.1");   //终端IP
            paramMap.put("notify_url",StaticProperty.NOTIFYURL);      //通知地址
            paramMap.put("trade_type", "NATIVE ");      //交易类型
            //签名
            String xmlParam = WXPayUtil.generateSignedXml(paramMap, StaticProperty.PARTNERKEY);
            //发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置成https请求
            httpClient.setHttps(true);
            //设置要传输的xml参数
            httpClient.setXmlParam(xmlParam);
            //请求操作
            httpClient.post();
            //获取响应数据(包含二维码支付地址信息)
            String content = httpClient.getContent();
            //将响应数据转换成Map结构
            Map<String,String> resultMap = WXPayUtil.xmlToMap(content);
            System.out.println(resultMap);
            Map<String,String> responseMap = new HashMap<String,String>();
            //二维码地址
            responseMap.put("code_url",resultMap.get("code_url"));
            //支付金额
            responseMap.put("money",money);
            //商户订单号
            responseMap.put("outtradeno",outtradeno);
            return  responseMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
