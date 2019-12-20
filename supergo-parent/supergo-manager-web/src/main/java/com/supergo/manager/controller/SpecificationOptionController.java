package com.supergo.manager.controller;

import com.supergo.feign.ApiSpecificationOptionFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.SpecificationOption;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  规格信息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/specificationOption")
@Api(value = "规格信息增删改查表现层", protocols = "http", description = "规格信息增删改查表现层")
public class SpecificationOptionController {

    /**
     * 功能描述: 规格调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiSpecificationOptionFeign apiSpecificationOptionFeign;


    /***
     * 增加SpecificationOption
     * @param specificationOption
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增规格", notes = "接收规格，新增规格信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增规格成功"),
            @ApiResponse(code = 500, message = "新增规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "规格信息", value = "传入json格式", required = false) SpecificationOption specificationOption){
        return apiSpecificationOptionFeign.add(specificationOption);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除规格", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除规格成功"),
            @ApiResponse(code = 500, message = "批量删除规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的规格id数组", type = "Long[]") Long[] ids){
        return apiSpecificationOptionFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param specificationOption
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改规格", notes = "接收规格信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改规格成功"),
            @ApiResponse(code = 500, message = "修改规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "规格信息", value = "传入json格式", required = false) SpecificationOption specificationOption){
        return apiSpecificationOptionFeign.update(specificationOption);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询规格", notes = "接收规格id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "规格id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询规格成功"),
            @ApiResponse(code = 500, message = "根据id查询规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiSpecificationOptionFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "规格分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询规格成功"),
            @ApiResponse(code = 500, message = "条件分页查询规格失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "规格对象") SpecificationOption specificationOption) {
        return apiSpecificationOptionFeign.getAll(pageNum,size,specificationOption);
    }

}
