package com.supergo.manager.controller;

import com.supergo.common.pojo.Provinces;
import com.supergo.feign.ApiProvincesFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  省份信息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/provinces")
@Api(value = "省份信息增删改查表现层", protocols = "http", description = "省份信息增删改查表现层")
public class ProvincesController {

    /**
     * 功能描述: 省份调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiProvincesFeign apiProvincesFeign;


    /***
     * 增加Provinces
     * @param provinces
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增省份", notes = "接收省份，新增省份信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增省份成功"),
            @ApiResponse(code = 500, message = "新增省份失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "省份信息", value = "传入json格式", required = false) Provinces provinces){
        return apiProvincesFeign.add(provinces);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除省份", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除省份成功"),
            @ApiResponse(code = 500, message = "批量删除省份失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的省份id数组", type = "Integer[]")  Integer[] ids){
        return apiProvincesFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param provinces
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改省份", notes = "接收省份信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改省份成功"),
            @ApiResponse(code = 500, message = "修改省份失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "省份信息", value = "传入json格式", required = false) Provinces provinces){
        return apiProvincesFeign.update(provinces);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询省份", notes = "接收省份id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "省份id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询省份成功"),
            @ApiResponse(code = 500, message = "根据id查询省份失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Integer id){
        return apiProvincesFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "省份分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询省份成功"),
            @ApiResponse(code = 500, message = "条件分页查询省份失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "省份对象") Provinces provinces) {
        return apiProvincesFeign.getAll(pageNum,size,provinces);
    }

}
