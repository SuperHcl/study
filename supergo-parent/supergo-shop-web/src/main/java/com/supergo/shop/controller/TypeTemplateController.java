package com.supergo.shop.controller;

import com.supergo.common.pojo.TbTypeTemplate;
import com.supergo.feign.ApiTypeTemplateFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  模板增删改查
 *
 * @param:
 * @return:
 * @auther: wesker
 * @date: 6/17/2019 9:37 AM
 */
@RestController
@RequestMapping(value = "/typeTemplate")
public class TypeTemplateController {

    /**
     * 功能描述: 模板调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiTypeTemplateFeign apiTypeTemplateFeign;


    /*****
     *
     *
     */
    /**
     * 功能描述: 获取规格选项的组装集合数据
     * URL:/typeTemplate/specification/option/{id}
     * 调用Service
     * 将数据返回到页面->JSON
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/specification/option/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取规格选项", notes = "接收规格选项id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "规格选项 id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id获取规格选项成功"),
            @ApiResponse(code = 500, message = "根据id查询规格选项失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getMapById(@PathVariable(value = "id") Long typeTemplateId) {
        return apiTypeTemplateFeign.findSpecOptionsList(typeTemplateId);
    }


    /**
     * 功能描述: 增加TypeTemplate
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加TypeTemplate", notes = "接收TypeTemplate,新增TypeTemplate信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增TypeTemplate成功"),
            @ApiResponse(code = 500, message = "新增TypeTemplate失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "TypeTemplate信息", value = "传入json格式", required = false) TbTypeTemplate typeTemplate) {
        return apiTypeTemplateFeign.add(typeTemplate);
    }


    /**
     * 功能描述: 删除操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除TypeTemplate", notes = "接收数组TypeTemplate")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除TypeTemplate成功"),
            @ApiResponse(code = 500, message = "批量删除TypeTemplate失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的TypeTemplate id数组", type = "Long[]") Long[] ids) {
        return apiTypeTemplateFeign.delete(ids);
    }

    /**
     * 功能描述: 修改操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改TypeTemplate", notes = "接收TypeTemplate信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改TypeTemplate成功"),
            @ApiResponse(code = 500, message = "修改TypeTemplate失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "TypeTemplate信息", value = "传入json格式", required = false) TbTypeTemplate typeTemplate) {
        return apiTypeTemplateFeign.update(typeTemplate);
    }

    /**
     * 功能描述: 根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询typeTemplate", notes = "接收typeTemplate id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "typeTemplate id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询typeTemplate成功"),
            @ApiResponse(code = 500, message = "根据id查询typeTemplate失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiTypeTemplateFeign.getById(id);
    }

    /**
     * 功能描述: 集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "typeTemplate分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询typeTemplate成功"),
            @ApiResponse(code = 500, message = "条件分页查询typeTemplate失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getAll(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                             @RequestBody(required = false) @ApiParam(name = "typeTemplate对象") TbTypeTemplate typeTemplate) {
        return apiTypeTemplateFeign.getAll(pageNum, size, typeTemplate);
    }

}
