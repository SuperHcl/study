package com.supergo.manager.controller;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.feign.ApiAttrFeign;
import com.supergo.http.HttpResult;
import com.supergo.page.PageResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 扩展属性增删改查
 * @Author jackhu
 * @Date 9/25/2019 10:17 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/attr")
@Api(value = "扩展属性信息增删改查表现层", protocols = "http")
public class AttrController {

    /**
     * @Description 注入扩展属性service对象
     * @Author jackhu
     * @Date 9/25/2019 10:18 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiAttrFeign apiAttrFeign;

    /**
     * @Description 根据ID查询扩展属性
     * @Author jackhu
     * @Date 9/25/2019 10:19 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询扩展属性", notes = "接收扩展属性id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "扩展属性id", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增扩展属性成功"),
            @ApiResponse(code = 500, message = "新增扩展属性失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult findAttrById(@PathVariable("id") Integer id) {
        TbAttribute tbAttribute = apiAttrFeign.findAttrById(id);
        return HttpResult.ok(tbAttribute);
    }

    /**
     * @Description 根据分页查询扩展属性
     * @Author jackhu
     * @Date 9/25/2019 10:19 AM
     * @Param [page, rows]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询区域成功"),
            @ApiResponse(code = 500, message = "条件分页查询区域失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findAllAttr(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        PageResult pageResult = apiAttrFeign.findAllAttr(page, rows);
        return HttpResult.ok(pageResult);
    }

    /**
     * @Description 添加扩展属性
     * @Author jackhu
     * @Date 9/25/2019 10:19 AM
     * @Param [attr]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增扩展属性", notes = "新增扩展属性")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增扩展属性成功"),
            @ApiResponse(code = 500, message = "新增扩展属性失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult insertAttribute(@RequestBody TbAttribute attr) {
        Integer id = apiAttrFeign.insertAttr(attr);
        return HttpResult.ok(id);
    }

    /**
     * @Description 修改扩展属性
     * @Author  jackhu
     * @Date 9/25/2019 10:20 AM
     * @Param [attr]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改扩展属性", notes = "接收扩展属性对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改扩展属性成功"),
            @ApiResponse(code = 500, message = "修改扩展属性失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
            @ApiResponse(code = 404, message = "请求未找到")
    })
    public HttpResult updateAttribute(@RequestBody TbAttribute attr) {
        return HttpResult.ok(apiAttrFeign.updateAttr(attr));
    }

}
