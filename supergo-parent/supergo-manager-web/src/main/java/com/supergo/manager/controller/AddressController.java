package com.supergo.manager.controller;

import com.supergo.common.pojo.Address;
import com.supergo.feign.ApiAddressFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 地址信息增删改查
 * @Author  jackhu
 * @Date 9/25/2019 10:13 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/address")
@Api(value = "地址信息增删改查表现层", protocols = "http", description = "地址信息增删改查表现层")
public class AddressController {

    /**
     * @Description 地址调用接口
     * @Author  jackhu
     * @Date 9/25/2019 10:13 AM
     * @Param 
     * @Return 
     * @Exception   
     */
    @Autowired
    private ApiAddressFeign apiAddressFeign;


    /**
     * @Description 新增地址
     * @Author  jackhu
     * @Date 9/25/2019 10:13 AM
     * @Param [address]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增地址", notes = "接收地址，新增地址信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增地址成功"),
            @ApiResponse(code = 500, message = "新增地址失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "地址信息", value = "传入json格式", required = false) Address address) {
        return apiAddressFeign.add(address);
    }


    /**
     * @Description 地址删除
     * @Author  jackhu
     * @Date 9/25/2019 10:13 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除地址", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除地址成功"),
            @ApiResponse(code = 500, message = "批量删除地址失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的地址id数组", type = "Long[]") Long[] ids) {
        return apiAddressFeign.delete(ids);
    }

   /**
    * @Description 地址修改
    * @Author  jackhu
    * @Date 9/25/2019 10:13 AM
    * @Param [address]
    * @Return com.supergo.http.HttpResult
    * @Exception
    */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改地址", notes = "接收地址信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改地址成功"),
            @ApiResponse(code = 500, message = "修改地址失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "地址信息", value = "传入json格式", required = false) Address address) {
        return apiAddressFeign.update(address);
    }

    /**
     * @Description 根据ID查询地址信息
     * @Author  jackhu
     * @Date 9/25/2019 10:14 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询地址", notes = "接收地址id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "地址id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询地址成功"),
            @ApiResponse(code = 500, message = "根据id查询地址失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiAddressFeign.getById(id);
    }

    /**
     * @Description 分页查询地址信息
     * @Author  jackhu
     * @Date 9/25/2019 10:14 AM
     * @Param [pageNum, size, address]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "地址分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询地址成功"),
            @ApiResponse(code = 500, message = "条件分页查询地址失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "地址对象") Address address) {
        return apiAddressFeign.getAll(pageNum, size, address);
    }

}
