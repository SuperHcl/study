package com.supergo.manager.controller;

import com.supergo.common.pojo.Content;
import com.supergo.feign.ApiContentFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 内容信息增删改查
 * @Author  jackhu
 * @Date 9/25/2019 11:01 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/content")
@Api(value = "内容信息增删改查表现层", protocols = "http", description = "内容信息增删改查表现层")
public class ContentController {

    /**
     * @Description 内容调用接口
     * @Author  jackhu
     * @Date 9/25/2019 11:01 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiContentFeign apiContentFeign;


    /**
     * @Description 增加内容对象
     * @Author  jackhu
     * @Date 9/25/2019 11:03 AM
     * @Param [content]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增内容", notes = "接收内容，新增内容信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增内容成功"),
            @ApiResponse(code = 500, message = "新增内容失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "内容信息", value = "传入json格式", required = false) Content content){
        return apiContentFeign.add(content);
    }


    /**
     * 功能描述:删除操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除内容", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除内容成功"),
            @ApiResponse(code = 500, message = "批量删除内容失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的内容id数组", type = "Long[]")  Long[] ids){
        return apiContentFeign.delete(ids);
    }

    /**
     * 功能描述:修改操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改内容", notes = "接收内容信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改内容成功"),
            @ApiResponse(code = 500, message = "修改内容失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "内容信息", value = "传入json格式", required = false) Content content){
        return apiContentFeign.update(content);
    }

    /**
     * 功能描述: 根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询内容", notes = "接收内容id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "地址id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询内容成功"),
            @ApiResponse(code = 500, message = "根据id查询内容失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiContentFeign.getById(id);
    }

    /**
     * 功能描述：集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "内容分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询内容成功"),
            @ApiResponse(code = 500, message = "条件分页查询内容失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "内容对象") Content content) {
        return apiContentFeign.getAll(pageNum,size,content);
    }

}
