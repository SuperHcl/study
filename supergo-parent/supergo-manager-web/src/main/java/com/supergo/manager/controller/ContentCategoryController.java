package com.supergo.manager.controller;

import com.supergo.common.pojo.ContentCategory;
import com.supergo.feign.ApiContentCategoryFeign;

import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 内容分类增删改查
 * @Author jackhu
 * @Date 9/25/2019 10:35 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/categorys")
@Api(value = "分类增删改查表现层", protocols = "http", description = "分类增删改查表现层")
public class ContentCategoryController {

    /**
     * @Description 注入内容分类接口
     * @Author jackhu
     * @Date 9/25/2019 10:35 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiContentCategoryFeign apiContentCategoryFeign;

    /**
     * @Description 查询所有内容分类
     * @Author jackhu
     * @Date 9/25/2019 10:35 AM
     * @Param []
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有内容分类", notes = "查询所有内容分类")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询所有内容分类成功"),
            @ApiResponse(code = 500, message = "查询所有内容分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list() {
        return apiContentCategoryFeign.list();
    }

    /**
     * @Description 新增内容分类
     * @Author jackhu
     * @Date 9/25/2019 10:36 AM
     * @Param [contentCategory]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加内容分类", notes = "添加内容分类")
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加内容分类成功"),
            @ApiResponse(code = 500, message = "添加内容分类城市失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody @ApiParam(name = "内容分类对象") ContentCategory contentCategory) {
        return apiContentCategoryFeign.add(contentCategory);
    }


    /**
     * @Description 删除内容分类
     * @Author jackhu
     * @Date 9/25/2019 10:36 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除内容分类", notes = "删除内容分类")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除内容分类成功"),
            @ApiResponse(code = 500, message = "删除内容分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的内容分类id数组", type = "Integer[]") Integer[] ids) {
        return apiContentCategoryFeign.delete(ids);
    }

    /**
     * @Description 修改内容分类
     * @Author jackhu
     * @Date 9/25/2019 10:36 AM
     * @Param [contentCategory]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改内容分类", notes = "修改内容分类")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改内容分类成功"),
            @ApiResponse(code = 500, message = "修改内容分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody @ApiParam(name = "内容分类对象") ContentCategory contentCategory) {
        return apiContentCategoryFeign.update(contentCategory);
    }

    /**
     * @Description 根据内容查询内容分类
     * @Author jackhu
     * @Date 9/25/2019 10:37 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据内容查询内容分类", notes = "根据内容查询内容分类")
    @ApiImplicitParam(paramType = "path", name = "id", value = "内容分类id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询内容分类成功"),
            @ApiResponse(code = 500, message = "查询内容分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Integer id) {
        return apiContentCategoryFeign.getById(id);
    }

    /**
     * @Description 分页查询内容分类
     * @Author jackhu
     * @Date 9/25/2019 10:37 AM
     * @Param [pageNum, size, contentCategory]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询内容分类", notes = "分页查询内容分类")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询内容分类成功"),
            @ApiResponse(code = 500, message = "条件分页查询内容分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getAll(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                             @RequestBody @ApiParam(name = "内容分类") ContentCategory contentCategory) {
        return apiContentCategoryFeign.getAll(pageNum, size, contentCategory);
    }

}
