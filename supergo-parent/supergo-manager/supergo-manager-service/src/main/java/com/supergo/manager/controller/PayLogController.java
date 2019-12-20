package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.PayLogService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 功能描述：支付日志增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:09
*/
@RestController
@RequestMapping("/payLog")
public class PayLogController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:10
    */
    @Autowired
    private PayLogService payLogService;

    /**
     * 功能描述：添加支付日志
     * @Param [payLog]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:10
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) PayLog payLog){
        try {
            payLogService.add(payLog);
            return HttpResult.ok("添加支付日志成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加支付日志失败!");
    }

    /**
     * 功能描述：删除支付日志
     * @Param [outTradeNos]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:10
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody String[] outTradeNos){
        try {
            payLogService.deleteByIds(outTradeNos);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改支付日志
     * @Param [payLog]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:10
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) PayLog payLog){
        try {
            payLogService.update(payLog);
            return HttpResult.ok("修改支付日志成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改支付日志失败!");
    }


    /**
     * 功能描述：根据ID查询支付日志
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:11
    */
    @RequestMapping("/getByOutTradeNo")
    public HttpResult getById(@RequestParam String id){
        return HttpResult.ok(payLogService.findOne(id));
    }


    /**
     * 功能描述：根据分页查询支付日志
     * @Param [pageNum, size, payLog]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:11
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) PayLog payLog){
        try {
            //分页查询
            PageResult result = payLogService.findPage(pageNum, size, payLog);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
