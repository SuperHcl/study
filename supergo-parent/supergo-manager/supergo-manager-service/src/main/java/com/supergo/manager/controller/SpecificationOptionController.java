package com.supergo.manager.controller;

import com.supergo.common.pojo.SpecificationOption;
import com.supergo.http.HttpResult;
import com.supergo.manager.service.SpecificationOptionService;
import com.supergo.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：规格参数增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:24
*/
@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:25
    */
    @Autowired
    private SpecificationOptionService specificationOptionService;

    /**
     * 功能描述：添加规格参数
     * @Param [specificationOption]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:26
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) SpecificationOption specificationOption){
        try {
            specificationOptionService.add(specificationOption);
            return HttpResult.ok("添加规格参数成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加规格参数失败!");
    }

    /**
     * 功能描述：删除规格参数
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:26
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        try {
            specificationOptionService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改规格参数
     * @Param [specificationOption]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:26
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) SpecificationOption specificationOption){
        try {
            specificationOptionService.update(specificationOption);
            return HttpResult.ok("修改地址成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改地址失败!");
    }

    /**
     * 功能描述：根据ID查询规格参数
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:26
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(specificationOptionService.findOne(id));
    }


    /**
     * 功能描述：根据分页查询规格参数
     * @Param [pageNum, size, specificationOption]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:26
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) SpecificationOption specificationOption){
        try {
            //分页查询
            PageResult result = specificationOptionService.findPage(pageNum, size, specificationOption);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
