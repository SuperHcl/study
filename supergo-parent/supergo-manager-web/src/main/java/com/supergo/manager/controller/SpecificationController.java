package com.supergo.manager.controller;

import com.supergo.common.pojo.Specification;
import com.supergo.feign.ApiSpecificationFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  规格数据信息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/specification")
public class SpecificationController {

    /**
     * 功能描述: 规格数据调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiSpecificationFeign apiSpecificationFeign;

    /***
     * 规格数据查询
     * @return
     */
    @RequestMapping(value = "/specificationList", method = RequestMethod.GET)
    @ApiOperation(value = "规格数据查询", notes = "规格数据查询")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询规格数据成功"),
            @ApiResponse(code = 500, message = "查询规格数据失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list() {
        return apiSpecificationFeign.list();
    }


    /***
     * 增加Specification
     * @param specification
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增规格数据", notes = "接收规格数据，新增规格数据信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增规格数据成功"),
            @ApiResponse(code = 500, message = "新增规格数据失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "规格数据信息", value = "传入json格式", required = false) Specification specification) {
        return apiSpecificationFeign.add(specification);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除规格数据", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除规格数据成功"),
            @ApiResponse(code = 500, message = "批量删除规格数据失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的规格数据id数组", type = "Long[]") Long[] ids) {
        return apiSpecificationFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param specification
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改规格数据", notes = "接收规格数据信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改规格数据成功"),
            @ApiResponse(code = 500, message = "修改规格数据失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "规格数据信息", value = "传入json格式", required = false) Specification specification) {
        return apiSpecificationFeign.update(specification);
    }

    @RequestMapping("/findByWhere")
    public HttpResult findByWhere(@RequestBody(required = false) Specification specification){
        return apiSpecificationFeign.findByWhere(specification);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询规格数据", notes = "接收规格数据id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "规格数据id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询规格数据成功"),
            @ApiResponse(code = 500, message = "根据id查询规格数据失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiSpecificationFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "规格数据分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询规格数据成功"),
            @ApiResponse(code = 500, message = "条件分页查询规格数据失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "规格数据对象") Specification specification) {
        HttpResult httpResult = apiSpecificationFeign.getAll(pageNum, size, specification);
        return httpResult;
    }

}
