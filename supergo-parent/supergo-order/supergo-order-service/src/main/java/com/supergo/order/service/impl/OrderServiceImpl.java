package com.supergo.order.service.impl;

import com.supergo.common.pojo.*;
import com.supergo.feign.ApiCartFeign;
import com.supergo.feign.ApiItemFeign;
import com.supergo.feign.ApiSSOFeign;
import com.supergo.http.HttpResult;
import com.supergo.order.mapper.OrderItemMapper;
import com.supergo.order.mapper.PayLogMapper;
import com.supergo.order.service.OrderService;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.order.mapper.OrderMapper;
import com.supergo.order.utils.RedisUtils;
import com.supergo.user.utils.CookieUtils;
import com.supergo.user.utils.DateUtil;
import com.supergo.user.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：订单service实现类
 *
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:35
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PayLogMapper payLogMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderItemMapper orderItemMapper;


    //注入购物车
    @Autowired
    private ApiCartFeign apiCartFeign;

    //注入sku商品
    @Autowired
    private ApiItemFeign apiItemFeign;

    @Autowired
    private ApiSSOFeign ssoFeign;


    @Autowired
    private HttpServletRequest request;


    @Override
    public void updateStatus(String userId, String transactionId, String payTime) {
        //用户支付日志
        PayLog payLog = (PayLog) redisUtils.hget("PayLog", userId);
        //日志入库  PayLog->OrderList=1,2,6,7
        payLog.setPayTime(DateUtil.str2Date(payTime));          //支付时间
        payLog.setTransactionId(transactionId); //交易号码
        payLog.setTradeState("1");          //支付状态
        payLogMapper.insertSelective(payLog);
        //清空Redis
        redisUtils.hdel("PayLog", userId);
        //取出日志中的订单集合   1,2,6,7
        String orderList = payLog.getOrderList();
        //分割订单集合
        String[] ids = orderList.split(",");
        //循环订单集合
        for (String id : ids) {
            //查询订单
            Order order = orderMapper.selectByPrimaryKey(Long.valueOf(id));
            //修改订单状态
            order.setStatus("2");   //已付款
            order.setPaymentTime(DateUtil.str2Date(payTime));   //支付时间
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }

    @Transactional
    public HttpResult addOrder(Order order) {

        int count = 0;

        //从cookie获取token
        String token = CookieUtils.getCookieValue(request, "SUPERGO_TOKEN");

        User user = ssoFeign.getUserByToken(token);

        List<Cart> cartList = apiCartFeign.findRedisCartList(user.getId()+"");

        //获取购物车信息
        //List<Cart> cartList = (List)result.getData();

        //2.扣减库存
        if(!apiItemFeign.deductionStock(cartList)){
            throw  new RuntimeException("库存不足！");
        }

        /****
         * 订单信息来源于购物车集合
         *
         *
         * 添加订单(几个订单?)
         *      有几个商家购物车信息，就创建几个订单数据
         *
         * 添加订单明细
         **/

        //记录订单总金额，用于记录日志
        double allTotalFee = 0.0;
        //记录订单的ID集合,用于做日志记录
        List<Long> orderIds = new ArrayList<Long>();
        //循环购物车集合
        for (Cart cart : cartList) {
            //创建被循环的购物车商家的订单信息
            Order cartOrder = new Order();
            //cartOrder.setStatus("1");
            //cartOrder.setSourceType("2");
            //cartOrder.setCreateTime(new Date());
            //cartOrder.setUpdateTime(order.getCreateTime());
            //cartOrder.setUserId(order.getUserId());
            //所有属性信息拷贝
            BeanUtils.copyProperties(order, cartOrder);
            //订单ID
            //cartOrder.setOrderId(new Date().getTime()+(int)(Math.random()*100000));
            cartOrder.setOrderId(idWorker.nextId());
            //seller_id:商家ID
            cartOrder.setSellerId(cart.getSellerId());
            //循环购物车中所有的商品
            Double totalFee = 0.0;
            for (OrderItem orderItem : cart.getOrderItems()) {
                //叠加商品的总金额
                totalFee += orderItem.getTotalFee();
                //订单ID设置
                orderItem.setOrderId(cartOrder.getOrderId());
                //设置主键ID
                //orderItem.setId(new Date().getTime()+(int)(Math.random()*100000));
                orderItem.setId(idWorker.nextId());
                //创建当前订单的订单明细信息
                orderItemMapper.insertSelective(orderItem);
            }
            //payment:总金额
            cartOrder.setPayment(totalFee);
            //所有订单总金额叠加,用于做日志记录
            allTotalFee += totalFee;
            //记录订单号
            orderIds.add(cartOrder.getOrderId());
            //添加订单
            count += orderMapper.insertSelective(cartOrder);
        }
        //用户支付方式为线上支付,记录支付日志
        if (order.getPaymentType().equalsIgnoreCase("1")) {
            //创建日志
            PayLog payLog = new PayLog();
            payLog.setOutTradeNo(String.valueOf(idWorker.nextId()));    //outtradeno
            payLog.setCreateTime(order.getCreateTime());    //创建时间
            payLog.setUserId(order.getUserId());    //用户ID
            payLog.setTradeState("0");  //待支付
            payLog.setPayType(order.getPaymentType());  //支付类型
            payLog.setTotalFee((long) (allTotalFee * 100));            //支付总金额=上面创建订单的总金额之和,单位：分
            //去除括号和空格  "1,2,6,7"
            String orderList = orderIds.toString().replace("[", "").replace("]", "").replace(" ", "");
            payLog.setOrderList(orderList);         //支付的订单编号集合=上面创建订单的ID集合
            //将日志记录到Redis中[暂时不需要存入到MySQL]
            //一个用于一般只允许存在一个未支付订单
            redisUtils.hset("PayLog", order.getUserId(), payLog);
        }
        if (count > 0) {
            //清空购物车
            redisUtils.hdel("Cart", order.getUserId());
        }


        return HttpResult.ok();
    }

    @Override
    public Object getPayLogByUserName(String username) {
        return redisUtils.hget("PayLog", username);
    }
}
