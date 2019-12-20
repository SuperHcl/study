package com.supergo.manager.controller;

import com.supergo.feign.ApiSellerFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Seller;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  商家信息增删改查
 *
 * @auther: jackhu
 * @date: 6/5/2019 11:37 AM
 */
@RestController
@RequestMapping(value = "/seller")
@Api(value = "商家信息增删改查表现层", protocols = "http", description = "商家信息增删改查表现层")
public class SellerController {

    /**
     * @Description 
     * @Author  jackhu
     * @Date 9/25/2019 3:48 PM
     * @Param 
     * @Return 
     * @Exception   
     */
    @Autowired
    private ApiSellerFeign apiSellerFeign;

    /**
     * 功能描述: 商家状态审核
     *
     * @param: 商家id、状态值
     * @return: HttpResult对象
     * @auther: jackhu
     * @date: 6/5/2019 11:42 AM
     */
    @RequestMapping(value = "/status/{id}/{status}", method = RequestMethod.GET)
    @ApiOperation(value = "修改商家状态", notes = "根据商家id值去修改状态status")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "商家id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "status", value = "要修改的状态值", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改商家状态成功"),
            @ApiResponse(code = 500, message = "修改商家状态失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult updateStatus(@PathVariable(value = "id") String id, @PathVariable(value = "status") String status) {
        //调用feign
        return apiSellerFeign.updateStatus(id, status);
    }

    /***
     * 增加Seller
     * @param seller
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增商家", notes = "接收商家对象，新增商家")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增商家成功"),
            @ApiResponse(code = 500, message = "新增商家失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "商家对象", value = "传入json格式", required = false) Seller seller) {
        return apiSellerFeign.add(seller);
    }

    /***
     * 增加Seller
     * @param seller
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改商家", notes = "接收商家对象")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改商家成功"),
            @ApiResponse(code = 500, message = "修改商家失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "商家对象", value = "传入json格式", required = false) Seller seller) {
        return apiSellerFeign.update(seller);
    }


    /**
     * 功能描述：删除操作
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:24
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除商家", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除商家成功"),
            @ApiResponse(code = 500, message = "批量删除商家失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的商家id数组", type = "String[]") String[] ids) {
        return apiSellerFeign.delete(ids);
    }

    /**
     * 功能描述：根据ID查询
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:29
    */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询商家", notes = "接收商家id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "商家id", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询商家成功"),
            @ApiResponse(code = 500, message = "根据id查询商家失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") String id) {
        return apiSellerFeign.getById(id);
    }

    /**
     * 功能描述：分页查询
     * @Param [seller, page, rows]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/24
     * @Time 15:29
    */
    @ApiOperation(value = "商家分页查询", notes = "接受分页参数page,rows")
    @RequestMapping(value = "/query/{page}/{rows}", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询商家成功"),
            @ApiResponse(code = 500, message = "条件分页查询商家失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult querySeller(@RequestBody(required = false) @ApiParam(name = "商家对象") Seller seller, @PathVariable("page") Integer page, @PathVariable("rows") Integer rows) {
        //分页查询
        return apiSellerFeign.findPage(seller, page, rows);
    }

}
