package com.supergo.manager.controller;

import com.supergo.common.pojo.Item;
import com.supergo.feign.ApiItemFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 商品信息增删改查
 * @Author  jackhu
 * @Date 9/25/2019 2:43 PM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/item")
@Api(value = "商品信息增删改查表现层", protocols = "http", description = "商品信息增删改查表现层")
public class ItemController {

    /**
     * @Description 商品调用接口
     * @Author  jackhu
     * @Date 9/25/2019 2:43 PM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiItemFeign apiItemFeign;

   /**
    * @Description 增加商品
    * @Author  jackhu
    * @Date 9/25/2019 2:44 PM
    * @Param [item]
    * @Return com.supergo.http.HttpResult
    * @Exception
    */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品", notes = "接收商品，新增商品信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增商品成功"),
            @ApiResponse(code = 500, message = "新增商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "商品信息", value = "传入json格式", required = false) Item item) {
        return apiItemFeign.add(item);
    }


    /**
     * 功能描述: 删除商品
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除商品", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除商品成功"),
            @ApiResponse(code = 500, message = "批量删除商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的商品id数组", type = "Long[]") Long[] ids) {
        return apiItemFeign.delete(ids);
    }

    /**
     * 功能描述: 修改商品
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改商品", notes = "接收商品信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改商品成功"),
            @ApiResponse(code = 500, message = "修改商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "商品信息", value = "传入json格式", required = false) Item item) {
        return apiItemFeign.update(item);
    }

    /**
     * 功能描述: 商品根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询商品", notes = "接收商品id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "商品id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询商品成功"),
            @ApiResponse(code = 500, message = "根据id查询商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiItemFeign.getById(id);
    }

    /**
     * 功能描述: 商品集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "商品分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询商品成功"),
            @ApiResponse(code = 500, message = "条件分页查询商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "商品对象") Item item) {
        return apiItemFeign.getAll(pageNum, size, item);
    }

    @RequestMapping("/updateStatus/{status}")
    public HttpResult updateStatus(@RequestBody Long[] ids, @PathVariable("status") String status) {
        return apiItemFeign.updateStatus(ids, status);
    }

}
