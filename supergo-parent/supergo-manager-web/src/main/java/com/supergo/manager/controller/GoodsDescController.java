package com.supergo.manager.controller;

import com.supergo.common.pojo.GoodsDesc;
import com.supergo.feign.ApiGoodsDescFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 产品详情信息增删改查
 * @Author jackhu
 * @Date 9/25/2019 2:08 PM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/goodsDesc")
@Api(value = "产品详情信息增删改查表现层", protocols = "http", description = "产品详情信息增删改查表现层")
public class GoodsDescController {

    /**
     * @Description 产品详情调用接口
     * @Author jackhu
     * @Date 9/25/2019 2:09 PM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiGoodsDescFeign apiGoodsDescFeign;


    /**
     * @Description 新增产品详情
     * @Author jackhu
     * @Date 9/25/2019 2:09 PM
     * @Param [goodsDesc]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增产品详情", notes = "接收详情，新增产品详情信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增产品详情成功"),
            @ApiResponse(code = 500, message = "新增产品详情失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "产品详情信息", value = "传入json格式", required = false) GoodsDesc goodsDesc) {
        return apiGoodsDescFeign.add(goodsDesc);
    }


    /**
     * @Description 根据id删除产品详情
     * @Author jackhu
     * @Date 9/25/2019 2:10 PM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除产品详情", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除产品详情成功"),
            @ApiResponse(code = 500, message = "批量删除产品详情失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的产品详情id数组", type = "Long[]") Long[] ids) {
        return apiGoodsDescFeign.delete(ids);
    }

    /**
     * @Description 修改产品详情
     * @Author jackhu
     * @Date 9/25/2019 2:10 PM
     * @Param [goodsDesc]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改产品详情", notes = "接收产品详情信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改产品详情成功"),
            @ApiResponse(code = 500, message = "修改产品详情失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "产品详情信息", value = "传入json格式", required = false) GoodsDesc goodsDesc) {
        return apiGoodsDescFeign.update(goodsDesc);
    }

    /**
     * @Description 根据id查询产品详情
     * @Author jackhu
     * @Date 9/25/2019 2:14 PM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询产品详情", notes = "接收地址id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "产品详情id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询产品详情成功"),
            @ApiResponse(code = 500, message = "根据id查询产品详情失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiGoodsDescFeign.getById(id);
    }

    /**
     * @Description 产品详情分页查询
     * @Author jackhu
     * @Date 9/25/2019 2:14 PM
     * @Param [pageNum, size, goodsDesc]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "产品详情分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询产品详情成功"),
            @ApiResponse(code = 500, message = "条件分页查询产品详情失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "产品详情对象") GoodsDesc goodsDesc) {
        return apiGoodsDescFeign.getAll(pageNum, size, goodsDesc);
    }

}
