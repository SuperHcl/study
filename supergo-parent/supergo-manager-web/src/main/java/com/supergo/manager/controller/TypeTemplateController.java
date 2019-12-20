package com.supergo.manager.controller;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.feign.ApiTypeTemplateFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.TbTypeTemplate;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能描述：模板信息增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/24
 * @Time 15:30
*/
@RestController
@RequestMapping("/template")
@Api(value = "模板信息增删改查表现层", protocols = "http", description = "模板信息增删改查表现层")
public class TypeTemplateController {

    /**
     * 功能描述：注入service服务对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:30
    */
    @Autowired
    private ApiTypeTemplateFeign apiTypeTemplateFeign;

    /**
     * 需求：商品录入，根据不同的规格选项组合成不同sku商品，因此查询规格选项
     */
    @RequestMapping("/findSpecOptionsList")
    public HttpResult findSpecOptionsList(@RequestParam("typeId") Long typeId) {
        //调用服务层
        return apiTypeTemplateFeign.findSpecOptionsList(typeId);
    }

    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id) {
        return apiTypeTemplateFeign.getById(id);
    }

    /**
     * 功能描述：增加TypeTemplate
     * @Param [typeTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:30
    */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增模板", notes = "接收模板，新增模板信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增模板成功"),
            @ApiResponse(code = 500, message = "新增模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "模板信息", value = "传入json格式", required = false) TbTypeTemplate typeTemplate) {
        return apiTypeTemplateFeign.add(typeTemplate);
    }

    /**
     * 功能描述：删除操作
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:30
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除模板", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除模板成功"),
            @ApiResponse(code = 500, message = "批量删除模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的模板id数组", type = "Long[]") Long[] ids) {
        return apiTypeTemplateFeign.delete(ids);
    }

    /**
     * 功能描述：修改操作
     * @Param [typeTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:30
    */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改模板", notes = "接收模板信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改模板成功"),
            @ApiResponse(code = 500, message = "修改模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "模板信息", value = "传入json格式", required = false) TbTypeTemplate typeTemplate) {
        return apiTypeTemplateFeign.update(typeTemplate);
    }

    /**
     * 功能描述：集合查询
     * @Param [pageNum, size, typeTemplate]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:30
    */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "模板分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询模板成功"),
            @ApiResponse(code = 500, message = "条件分页查询模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "模板对象") TbTypeTemplate typeTemplate) {
        return apiTypeTemplateFeign.getAll(pageNum, size, typeTemplate);
    }

    @RequestMapping("/getAllAttrNotPage")
    public HttpResult getAllAttrNotPage() {
        List<TbAttribute> lists =  apiTypeTemplateFeign.getAllAttrNotPage();
        return HttpResult.ok(lists);
    }
}
