package com.supergo.manager.controller;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.manager.service.TypeTemplateService;
import com.supergo.http.HttpResult;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.TbTypeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述：类型模板增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:27
*/
@RestController
@RequestMapping("/template")
public class TypeTemplateController {

    /**
     * 功能描述：注入service服务对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:27
    */
    @Autowired
    private TypeTemplateService typeTemplateService;

    /**
     * 功能描述：根据模板ID查询规格数据
     * @Param [typeId]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:27
    */
    @RequestMapping("/findSpecOptionsList")
    public HttpResult findSpecOptionsList(@RequestParam("typeId") Long typeId) {
        return HttpResult.ok(typeTemplateService.findSpecOptionsList(typeId));
    }

    /**
     * 功能描述：根据ID查询类型模板
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:28
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(typeTemplateService.findOne(id));
    }

    /**
     * 功能描述：查询全部类型模板
     * @Param []
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:28
    */
    @RequestMapping("/findAll")
    public HttpResult findAll() {
        return HttpResult.ok(typeTemplateService.findAll());
    }

    /**
     * 功能描述：删除类型模板
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:28
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody(required = false) Long[] ids) {
        try {
            typeTemplateService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    @RequestMapping("/findByWhere")
    public HttpResult findByWhere(@RequestBody(required = false) TbTypeTemplate tbTypeTemplate) {
        return HttpResult.ok(typeTemplateService.findByWhere(tbTypeTemplate));
    }

    /**
     * 功能描述：添加类型模板
     * @Param [typeTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:28
    */
    @RequestMapping("/add")
    HttpResult add(@RequestBody(required = false) TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.add(typeTemplate);
            return HttpResult.ok("添加类型模板成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加类型模板失败!");
    }

    /**
     * 功能描述：修改类型模板
     * @Param [typeTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:28
    */
    @RequestMapping("/update")
    HttpResult update(@RequestBody(required = false) TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
            return HttpResult.ok("修改地址成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改地址失败!");
    }

    /**
     * 功能描述：根据分页查询类型模板
     * @Param [pageNum, size, typeTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:29
    */
    @RequestMapping("/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) TbTypeTemplate typeTemplate) {
        try {
            //分页查询
            PageResult result = typeTemplateService.getAllTemplate(pageNum, size, typeTemplate);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }

    @RequestMapping("/queryAllAttrNotPage")
    public List<TbAttribute> getAllAttrNotPage(){
        return typeTemplateService.getAllAttrNotPage();
    }
}
