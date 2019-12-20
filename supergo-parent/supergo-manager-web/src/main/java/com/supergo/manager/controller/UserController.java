package com.supergo.manager.controller;

import com.supergo.feign.ApiUserFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  用户信息增删改查
 **@param:
 * @return:
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "用户信息增删改查表现层", protocols = "http", description = "用户信息增删改查表现层")
public class UserController {


    /**
     * 功能描述: 用户调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiUserFeign apiUserFeign;

    /**
     * 功能描述: 用户增加
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增用户信息", notes = "接收用户信息，新增用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增用户信息成功"),
            @ApiResponse(code = 500, message = "新增用户信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "用户信息", value = "传入json格式", required = false) User user){
        return apiUserFeign.add(user);
    }


    /**
     * 功能描述: 用户删除
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除用户信息", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除用户信息成功"),
            @ApiResponse(code = 500, message = "批量删除用户信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的用户信息id数组", type = "Long[]") Long[] ids){
        return apiUserFeign.delete(ids);
    }

    /**
     * 功能描述: 用户修改
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户信息", notes = "接收用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改用户成功"),
            @ApiResponse(code = 500, message = "修改用户失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "用户信息", value = "传入json格式", required = false) User user){
        return apiUserFeign.update(user);
    }

    /**
     * 功能描述: 用户根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询用户信息", notes = "接收用户信息id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "用户信息id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询用户信息成功"),
            @ApiResponse(code = 500, message = "根据id查询用户信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiUserFeign.getById(id);
    }

    /**
     * 功能描述: 用户集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "用户信息分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询用户信息成功"),
            @ApiResponse(code = 500, message = "条件分页查询用户信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "用户信息对象") User user) {
        return apiUserFeign.getAll(pageNum,size,user);
    }

}
