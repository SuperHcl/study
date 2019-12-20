package com.supergo.manager.controller;

import com.supergo.common.pojo.OrderItem;
import com.supergo.feign.ApiOrderItemFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 订单商品信息增删改查
 * @Author  jackhu
 * @Date 9/26/2019 10:58 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/orderItem")
@Api(value = "商品订单信息增删改查表现层", protocols = "http", description = "商品订单订单信息增删改查表现层")
public class OrderItemController {

    /**
     * @Description 注入商品订单接口
     * @Author  jackhu
     * @Date 9/26/2019 11:02 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiOrderItemFeign apiOrderItemFeign;


    /**
     * @Description 新增商品订单
     * @Author  jackhu
     * @Date 9/26/2019 11:03 AM
     * @Param [orderItem]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增订单商品", notes = "接收订单商品对象，新增订单商品")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增订单商品成功"),
            @ApiResponse(code = 500, message = "新增订单商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "订单商品对象", value = "传入json格式", required = false) OrderItem orderItem){
        return apiOrderItemFeign.add(orderItem);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除订单商品", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除订单商品成功"),
            @ApiResponse(code = 500, message = "批量删除订单商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的区域id数组", type = "Long[]") Long[] ids) {
        return apiOrderItemFeign.delete(ids);
    }
    /***
     * 修改操作
     * @param orderItem
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改订单商品", notes = "接收订单商品对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改订单商品成功"),
            @ApiResponse(code = 500, message = "修改订单商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "订单商品对象", value = "传入json格式", required = false) OrderItem orderItem){
        return apiOrderItemFeign.update(orderItem);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询订单商品", notes = "接收订单商品id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "订单商品id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询订单商品成功"),
            @ApiResponse(code = 500, message = "根据id查询订单商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id){
        return apiOrderItemFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "订单商品分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询订单商品成功"),
            @ApiResponse(code = 500, message = "条件分页查询订单商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "订单商品对象") OrderItem orderItem) {
        return apiOrderItemFeign.getAll(pageNum,size,orderItem);
    }
}
