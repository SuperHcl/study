package com.supergo.manager.controller;

import com.supergo.common.pojo.Cities;
import com.supergo.feign.ApiCitiesFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 城市信息增删改查
 * @Author  jackhu
 * @Date 9/25/2019 10:23 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/cities")
@Api(value = "城市信息", protocols = "http", description = "城市信息")
public class CitiesController {

    /**
     * @Description 注入城市service对象
     * @Author  jackhu
     * @Date 9/25/2019 10:23 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiCitiesFeign apiCitiesFeign;

    /**
     * @Description  城市增加Cities
     * @Author  jackhu
     * @Date 9/25/2019 10:23 AM
     * @Param [cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增城市", notes = "接收城市，新增城市信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增城市成功"),
            @ApiResponse(code = 500, message = "新增城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "城市信息", value = "传入json格式", required = false) Cities cities){
        return apiCitiesFeign.add(cities);
    }


    /**
     * @Description 城市信息删除操作
     * @Author  jackhu
     * @Date 9/25/2019 10:32 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除城市", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除城市成功"),
            @ApiResponse(code = 500, message = "批量删除城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的城市id数组", type = "Integer[]") Integer[] ids){
        return apiCitiesFeign.delete(ids);
    }

    /**
     * @Description 城市信息修改操作
     * @Author  jackhu
     * @Date 9/25/2019 10:33 AM
     * @Param [cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改城市", notes = "接收城市信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改城市成功"),
            @ApiResponse(code = 500, message = "修改城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "城市信息", value = "传入json格式", required = false) Cities cities){
        return apiCitiesFeign.update(cities);
    }

    /**
     * @Description 根据ID查询城市信息
     * @Author  jackhu
     * @Date 9/25/2019 10:33 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询城市", notes = "接收城市id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "地址id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询城市成功"),
            @ApiResponse(code = 500, message = "根据id查询城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiCitiesFeign.getById(id);
    }

    /**
     * @Description 分页查询城市信息
     * @Author  jackhu
     * @Date 9/25/2019 10:34 AM
     * @Param [pageNum, size, cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "城市分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询城市成功"),
            @ApiResponse(code = 500, message = "条件分页查询城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "城市对象") Cities cities) {
        return apiCitiesFeign.getAll(pageNum,size,cities);
    }

}
