package com.supergo.manager.controller;

import com.supergo.common.pojo.FreightTemplate;
import com.supergo.feign.ApiFreightTemplateFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 模板信息增删改查
 * @Author  jackhu
 * @Date 9/25/2019 11:23 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/freightTemplate")
@Api(value = "模板信息增删改查表现层", protocols = "http", description = "模板信息增删改查表现层")
public class FreightTemplateController {

    /**
     * @Description 注入模板接口
     * @Author  jackhu
     * @Date 9/25/2019 11:23 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiFreightTemplateFeign apiFreightTemplateFeign;


    /**
     * @Description 添加模板信息
     * @Author  jackhu
     * @Date 9/25/2019 11:24 AM
     * @Param [freightTemplate]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    @ApiOperation(value = "新增模板", notes = "接收模板对象，新增模板")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增模板成功"),
            @ApiResponse(code = 500, message = "新增模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "模板对象", value = "传入json格式", required = false) FreightTemplate freightTemplate){
        return apiFreightTemplateFeign.add(freightTemplate);
    }


    /**
     * @Description 删除模板信息操作
     * @Author  jackhu
     * @Date 9/25/2019 11:27 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除模板", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除模板成功"),
            @ApiResponse(code = 500, message = "批量删除模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的模板id数组", type = "Long[]")  Long[] ids){
        return apiFreightTemplateFeign.delete(ids);
    }

    /**
     * @Description 修改模板模板
     * @Author  jackhu
     * @Date 9/25/2019 11:28 AM
     * @Param [freightTemplate]
     * @Return com.supergo.http.HttpResult
     * @Exception   
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改模板", notes = "接收模板对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改模板成功"),
            @ApiResponse(code = 500, message = "修改模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "模板对象", value = "传入json格式", required = false) FreightTemplate freightTemplate){
        return apiFreightTemplateFeign.update(freightTemplate);
    }

    /**
     * @Description 根据ID查询模板信息
     * @Author  jackhu
     * @Date 9/25/2019 11:29 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询模板", notes = "接收模板")
    @ApiImplicitParam(paramType = "path", name = "id", value = "模板id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询模板成功"),
            @ApiResponse(code = 500, message = "根据id查询模板失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiFreightTemplateFeign.getById(id);
    }

    /**
     * @Description 分页查询模板信息
     * @Author  jackhu
     * @Date 9/25/2019 11:38 AM
     * @Param [pageNum, size, freightTemplate]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
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
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "模板对象") FreightTemplate freightTemplate) {
        return apiFreightTemplateFeign.getAll(pageNum,size,freightTemplate);
    }

}
