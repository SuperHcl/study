package com.supergo.manager.controller;

import com.supergo.feign.ApiPayLogFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.PayLog;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  支付信息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/payLog")
@Api(value = "支付信息增删改查表现层", protocols = "http", description = "支付信息增删改查表现层")
public class PayLogController {

    /**
     * 功能描述: 支付调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiPayLogFeign apiPayLogFeign;


    /***
     * 增加PayLog
     * @param payLog
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增支付", notes = "接收支付，新增支付信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增支付成功"),
            @ApiResponse(code = 500, message = "新增支付失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "支付信息", value = "传入json格式", required = false) PayLog payLog){
        return apiPayLogFeign.add(payLog);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除支付", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除支付成功"),
            @ApiResponse(code = 500, message = "批量删除支付失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的支付id数组", type = "String[]") String[] ids){
        return apiPayLogFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param payLog
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改支付", notes = "接收支付信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改支付成功"),
            @ApiResponse(code = 500, message = "修改支付失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "支付信息", value = "传入json格式", required = false) PayLog payLog){
        return apiPayLogFeign.update(payLog);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询支付", notes = "接收支付id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "支付id", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询支付成功"),
            @ApiResponse(code = 500, message = "根据id查询支付失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") String id){
        return apiPayLogFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "支付分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询支付成功"),
            @ApiResponse(code = 500, message = "条件分页查询支付失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "支付对象") PayLog payLog) {
        return apiPayLogFeign.getAll(pageNum,size,payLog);
    }

}
