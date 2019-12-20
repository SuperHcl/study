package com.supergo.seckill.service.impl;

import com.supergo.common.pojo.SeckillOrder;
import com.supergo.common.pojo.User;
import com.supergo.feign.ApiSSOFeign;
import com.supergo.feign.ApiSeckillOrderFeign;
import com.supergo.feign.ApiWeixinPayFeign;
import com.supergo.http.HttpResult;
import com.supergo.http.HttpStatus;
import com.supergo.seckill.service.SeckillOrderService;
import com.supergo.user.utils.SeckillStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by on 2019/10/25.
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    //注入单点登录feign
    @Autowired
    private ApiSSOFeign ssoFeign;

    @Autowired
    private ApiSeckillOrderFeign apiSeckillOrderFeign;

    //注入微信支付
    @Autowired
    private ApiWeixinPayFeign apiWeixinPayFeign;

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @Override
    public HttpResult getUserByToken(String token) {

        User user = ssoFeign.getUserByToken(token);

        return HttpResult.ok(user);
    }

    /**
     * 提交订单
     * @param id
     * @param seckillId
     */

    @Override
    public void add(Long id, Long seckillId) {
        //提交订单
        apiSeckillOrderFeign.add(String.valueOf(id),seckillId);
    }

    /**
     * 秒杀订单状态查询
     * @param userId
     * @return
     */
    @Override
    public HttpResult getStatusByUserName(String userId) {

        SeckillStatus statusByUserName = apiSeckillOrderFeign.getStatusByUserName(userId);

        return HttpResult.ok(statusByUserName);
    }

    /***
     * 创建二维码
     * @return
     */
    @Override
    public HttpResult createNativeQrcode(Long userId) {


        //获取订单信息
        SeckillOrder seckillOrder = apiSeckillOrderFeign.getOrderByUserName(String.valueOf(userId));

        //return weixinPayService.createNative(String.valueOf(seckillOrder.getId()),String.valueOf(seckillOrder.getMoney()));
        return apiWeixinPayFeign.createNative(String.valueOf(seckillOrder.getId()),String.valueOf(seckillOrder.getMoney()));
    }
    /***
     * 查询支付状态
     * @param outtradeno:订单号
     */
    @Override
    public HttpResult queryPayStatus(String outtradeno,String userId) {
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

                //判断是否为空
                if(StringUtils.isNotBlank(tradeState)){


                //SUCCESS:支付成功   跳转到支付成功页面
                if(tradeState.equalsIgnoreCase("SUCCESS")){
                    //修改订单状态   transaction_id
                    //String userid,String transactionId,String payTime  time_end
                    String transactionId = resultMap.get("transaction_id");
                    String payTime = resultMap.get("time_end");
                    apiSeckillOrderFeign.updateStatus(userId,transactionId,payTime);
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
                        //超时,关闭订单，再删除订单
                        Map<String, String> closeResultMap = (Map<String, String>)apiWeixinPayFeign.closePay(outtradeno).getData();
                        if(closeResultMap.get("return_code").equalsIgnoreCase("SUCCESS")){
                            //判断业务结果
                            String resultCode = closeResultMap.get("result_code");
                            //SUCCESS：关闭成功
                            if(resultCode.equalsIgnoreCase("SUCCESS")){
                                //删除订单
                                apiSeckillOrderFeign.deleteOrder(userId);
                            }else if(resultCode.equalsIgnoreCase("FAIL")){
                                //FAIL:关闭失败
                                //ORDERPAID:已支付,修改订单状态
                                if(resultCode.equalsIgnoreCase("ORDERPAID")){
                                    //查询支付状态
                                    resultMap = (Map<String, String>)apiWeixinPayFeign.queryStatus(outtradeno).getData();
                                    //修改订单状态
                                    String transactionId = resultMap.get("transaction_id");
                                    String payTime = resultMap.get("time_end");
                                    apiSeckillOrderFeign.updateStatus(userId,transactionId,payTime);
                                    return HttpResult.ok("支付成功！");
                                }else if(resultCode.equalsIgnoreCase("ORDERCLOSED")){
                                    //ORDERCLOSED:删除订单
                                    apiSeckillOrderFeign.deleteOrder(userId);
                                }
                            }else{
                                //其他错误
                            }
                        }
                        return HttpResult.ok(HttpResult.error(HttpStatus.SC_PRECONDITION_FAILED,"其他错误"));
                    }
                    continue;
                }else{
                    //其他支付失败
                    return HttpResult.error("支付失败！");
                }

                }
                //订单不存在
                return HttpResult.error(HttpStatus.SC_NOT_FOUND,"订单不存在");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
