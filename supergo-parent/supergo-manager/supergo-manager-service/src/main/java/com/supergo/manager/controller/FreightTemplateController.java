package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.FreightTemplateService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.FreightTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：运营模板增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 16:48
*/
@RestController
@RequestMapping("/freightTemplate")
public class FreightTemplateController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/23
     * @Time 14:46
    */
    @Autowired
    private FreightTemplateService freightTemplateService;

    /**
     * 功能描述：添加运营模板
     * @Param [freightTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:49
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) FreightTemplate freightTemplate) {
        try {
            freightTemplateService.add(freightTemplate);
            return HttpResult.ok("添加模板成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加模板失败!");
    }

    /**
     * 功能描述：删除运营模板
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:49
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids) {
        try {
            freightTemplateService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改运营模板
     * @Param [freightTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:49
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) FreightTemplate freightTemplate) {
        try {
            freightTemplateService.update(freightTemplate);
            return HttpResult.ok("修改城市成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改城市失败!");
    }

    /**
     * 功能描述：根据ID查询运营模板
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:49
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(freightTemplateService.findOne(id));
    }

    /**
     * 功能描述：分页查询运营模板
     * @Param [pageNum, size, freightTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:50
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) FreightTemplate freightTemplate) {
        try {
            //分页查询
            PageResult result = freightTemplateService.findPage(pageNum, size, freightTemplate);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
