package com.supergo.manager.controller;

import com.supergo.common.pojo.Provinces;
import com.supergo.http.HttpResult;
import com.supergo.manager.service.ProvincesService;
import com.supergo.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 功能描述：省份增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:12
*/
@RestController
@RequestMapping("/provinces")
public class ProvincesController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:12
    */
    @Autowired
    private ProvincesService provincesService;

    /**
     * 功能描述：添加支付日志
     * @Param [provinces]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:12
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Provinces provinces){
        try {
            provincesService.add(provinces);
            return HttpResult.ok("添加省份成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加省份失败!");
    }


    /**
     * 功能描述：删除支付日志
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:12
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Integer[] ids){
        try {
            provincesService.deleteByIds(ids);
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
     * @Param [provinces]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:12
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Provinces provinces){
        try {
            provincesService.update(provinces);
            return HttpResult.ok("修改省份成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改省份失败!");
    }

    /**
     * 功能描述：根据ID查询支付日志
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:13
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Integer id){
        return HttpResult.ok(provincesService.findOne(id));
    }

    /**
     * 功能描述：根据分页查询支付日志
     * @Param [pageNum, size, provinces]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:13
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Provinces provinces){
        try {
            //分页查询
            PageResult result = provincesService.findPage(pageNum, size, provinces);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
