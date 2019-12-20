package com.supergo.manager.controller;

import com.supergo.manager.service.SecKillOrderService;
import com.supergo.http.HttpResult;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 功能描述：秒杀订单增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:17
*/
@RestController
@RequestMapping("/seckillOrder")
public class SecKillOrderController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:17
    */
    @Autowired
    private SecKillOrderService secKillOrderService;

    /**
     * 功能描述：添加秒杀订单
     * @Param [seckillOrder]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:17
    */
    @RequestMapping("/add")
    HttpResult add(@RequestBody(required = false) SeckillOrder seckillOrder){
        try {
            secKillOrderService.add(seckillOrder);
            return HttpResult.ok("添加秒杀订单成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加秒杀订单失败!");
    }

    /**
     * 功能描述：删除秒杀订单
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:17
    */
    @RequestMapping("/delete")
    HttpResult delete(@RequestBody Long[] ids){
        try {
            secKillOrderService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改秒杀订单
     * @Param [seckillOrder]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:18
    */
    @RequestMapping("/update")
    HttpResult update(@RequestBody(required = false) SeckillOrder seckillOrder){
        try {
            secKillOrderService.update(seckillOrder);
            return HttpResult.ok("修改秒杀订单成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改秒杀订单失败!");
    }


    /**
     * 功能描述：根据ID查询秒杀订单
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:18
    */
    @RequestMapping("/getById")
    HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(secKillOrderService.findOne(id));
    }

    /**
     * 功能描述：根据分页查询秒杀订单
     * @Param [pageNum, size, seckillOrder]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:18
    */
    @RequestMapping("/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) SeckillOrder seckillOrder){
        try {
            //分页查询
            PageResult result = secKillOrderService.findPage(pageNum, size, seckillOrder);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}