package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.OrderService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 功能描述：订单增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:04
*/
@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:04
    */
    @Autowired
    private OrderService orderService;

    /**
     * 功能描述：添加订单
     * @Param [order]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:04
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Order order){
        try {
            orderService.add(order);
            return HttpResult.ok("添加订单成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加订单失败!");
    }

/**
 * 功能描述：删除订单
 * @Param [ids]
 * @Return com.jmyp.http.HttpResult
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:04
*/
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        try {
            orderService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }


    /**
     * 功能描述：修改订单
     * @Param [order]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:04
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Order order){
        try {
            orderService.update(order);
            return HttpResult.ok("修改订单成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改订单失败!");
    }

    /**
     * 功能描述：根据ID查询订单
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:05
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(orderService.findOne(id));
    }


    /**
     * 功能描述：根据分页查询订单
     * @Param [pageNum, size, order]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:05
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Order order){
        try {
            //分页查询
            PageResult result = orderService.findPage(pageNum, size, order);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
