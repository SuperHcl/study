package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.OrderItemService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 功能描述：订单商品增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:06
*/
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:06
    */
    @Autowired
    private OrderItemService orderItemService;

    /**
     * 功能描述：添加订单商品
     * @Param [orderItem]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:06
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) OrderItem orderItem){
        try {
            orderItemService.add(orderItem);
            return HttpResult.ok("添加订单商品成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加订单商品失败!");
    }

    /**
     * 功能描述：删除订单商品
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:07
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        try {
            orderItemService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改订单商品
     * @Param [orderItem]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:07
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) OrderItem orderItem){
        try {
            orderItemService.update(orderItem);
            return HttpResult.ok("修改订单商品成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改订单商品失败!");
    }

    /**
     * 功能描述：根据ID查询订单商品
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:07
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(orderItemService.findOne(id));
    }

    /**
     * 功能描述：根据分页查询订单商品
     * @Param [pageNum, size, orderItem]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:07
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) OrderItem orderItem){
        try {
            //分页查询
            PageResult result = orderItemService.findPage(pageNum, size, orderItem);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
