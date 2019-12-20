package com.supergo.manager.controller;

import com.supergo.common.pojo.Specification;
import com.supergo.http.HttpResult;
import com.supergo.manager.service.SpecificationService;
import com.supergo.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * 功能描述：规格增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:23
*/
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:23
    */
    @Autowired
    private SpecificationService specificationService;

    /**
     * 功能描述：删除规格
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:23
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody(required = false) Long[] ids){
        try {
            specificationService.deleteSpecification(Arrays.asList(ids));
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    @RequestMapping("/findByWhere")
    public HttpResult findByWhere(@RequestBody(required = false) Specification specification){
        return HttpResult.ok(specificationService.findByWhere(specification));
    }

    @RequestMapping("/specificationList")
    public HttpResult list(){
        List<Map<String, Object>> list = specificationService.specificationList();
        return HttpResult.ok(list);
    }

    /**
     * 功能描述：添加规格
     * @Param [specification]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:23
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Specification specification){
        try {
            specificationService.addSpecification(specification);
            return HttpResult.ok("添加规格成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加规格失败!");
    }

    /**
     * 功能描述：修改规格
     * @Param [specification]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:24
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Specification specification){
        try {
            specificationService.updateSpecification(specification);
            return HttpResult.ok("修改规格成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改规格失败!");
    }

    /**
     * 功能描述：根据ID查询规格
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:24
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(specificationService.getBySpecificationId(id));
    }

    /**
     * 功能描述：根据分页查询规格
     * @Param [pageNum, size, specification]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:24
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Specification specification){
        try {
            //分页查询
            PageResult result = specificationService.getAllSpecification(pageNum, size, specification);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
