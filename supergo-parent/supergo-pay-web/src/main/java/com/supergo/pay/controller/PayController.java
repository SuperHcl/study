package com.supergo.pay.controller;

import com.supergo.common.pojo.PayLog;
import com.supergo.feign.ApiOrderFeign;
import com.supergo.feign.ApiWeixinPayFeign;
import com.supergo.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/26 16:19
 *
 ****/
@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private ApiWeixinPayFeign apiWeixinPayFeign;

    @Autowired
    private ApiOrderFeign apiOrderFeign;

    /***
     * 查询支付状态
     * @param outtradeno:商户订单号
     */
    @RequestMapping(value = "/queryStatus")
    public HttpResult queryStatus(@RequestParam("outtradeno") String outtradeno){
        //获取用户登录名
        // String userid = SecurityContextHolder.getContext().getAuthentication().getName();
        String userId = "123";
        try {
            //累计=5
            int count=0;
            while (true){
                //查询支付状态
                Map<String, String> resultMap = (Map<String, String>)apiWeixinPayFeign.queryStatus(outtradeno).getData();
                System.out.println(resultMap);
                //获取业务结果  result_code:SUCCESS  成功
                //判断交易状态   trade_state:SUCCESS 交易成功
                //                          REFUND—转入退款  证书
                //                          NOTPAY—未支付
                //                          CLOSED—已关闭
                //                          REVOKED—已撤销（付款码支付）
                //                          USERPAYING--用户支付中（付款码支付）
                //                          PAYERROR--支付失败(其他原因，如银行返回失败)
                String tradeState = resultMap.get("trade_state");
                //SUCCESS:支付成功   跳转到支付成功页面
                if(tradeState.equalsIgnoreCase("SUCCESS")){
                    //修改订单状态   transaction_id
                    //String userid,String transactionId,String payTime  time_end
                    String transactionId = resultMap.get("transaction_id");
                    String payTime = resultMap.get("time_end");
                    apiOrderFeign.updateStatus(userId,transactionId,payTime);
                    return HttpResult.ok("支付成功");
                }else if(tradeState.equalsIgnoreCase("NOTPAY") || tradeState.equalsIgnoreCase("USERPAYING")){
                    //NOTPAY|USERPAYING  继续查询
                    System.out.println("继续查询....");
                    //休眠3秒
                    Thread.sleep(3000);
                    //累加
                    count++;
                    //如果count>=5,超过15秒
                    if(count>=5){
                        //尝试关闭二维码支付->调用腾讯
                        //超时
                        return HttpResult.ok("504");
                    }
                    continue;
                }else{
                    //其他支付失败
                    return HttpResult.error("失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/createNative")
    public HttpResult createNative(){
        //根据用户名字查询用户支付日志
        // String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String userName = "123";
        PayLog payLog = (PayLog)apiOrderFeign.getPayLogByUserName(userName).getData();
        //String outtradeno="No"+ (int)(Math.random()*1000000);
        //return weixinPayService.createNative(outtradeno,"1");
        return apiWeixinPayFeign.createNative(payLog.getOutTradeNo(),"1");
    }
}