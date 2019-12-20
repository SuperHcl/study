package com.supergo.manager.controller;

import com.supergo.common.pojo.Goods;
import com.supergo.feign.ApiGoodsFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description 产品信息增删改查
 * @Author  jackhu
 * @Date 9/25/2019 11:39 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/goods")
@Api(value = "产品信息增删改查表现层", protocols = "http", description = "产品信息增删改查表现层")
public class GoodsController {


   /**
    * @Description 产品接口
    * @Author  jackhu
    * @Date 9/25/2019 11:41 AM
    * @Param
    * @Return
    * @Exception
    */
    @Autowired
    private ApiGoodsFeign apiGoodsFeign;

    /**
     * @Description 查询所有产品信息
     * @Author  jackhu
     * @Date 9/25/2019 11:42 AM
     * @Param []
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "查询所有产品信息")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询产品成功"),
            @ApiResponse(code = 500, message = "查询产品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findAll(){
        return apiGoodsFeign.findAll();
    }


    /**
     * @Description 产品信息分页查询
     * @Author  jackhu
     * @Date 9/25/2019 11:55 AM
     * @Param [goods, page, rows]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "产品信息分页查询",notes = "接受分页参数page,rows")
    @RequestMapping(value = "/query/{page}/{rows}",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询产品成功"),
            @ApiResponse(code = 500, message = "条件分页查询产品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult queryBrand(@RequestBody(required = false) @ApiParam(name = "产品对象") Goods goods, @PathVariable Integer page, @PathVariable Integer rows){
        //分页查询
        return apiGoodsFeign.findPage(goods,page,rows);
    }
    
    /**
     * @Description 产品管理之新建
     * @Author  jackhu
     * @Date 9/25/2019 12:05 PM
     * @Param [goods]
     * @Return com.supergo.http.HttpResult
     * @Exception   
     */
    @ApiOperation(value = "产品管理之新建",notes = "接受对象参数brand")
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增产品产品成功"),
            @ApiResponse(code = 500, message = "新增产品产品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult saveOrUpdate(@RequestBody(required = false) @ApiParam(name = "产品对象", value = "传入json格式", required = false) Goods goods){
        return  apiGoodsFeign.saveOrUpdate(goods);
    }

    /**
     * @Description 产品管理之删除
     * @Author  jackhu
     * @Date 9/25/2019 12:05 PM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "产品管理之删除",notes = "接受对象参数ids")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除产品成功"),
            @ApiResponse(code = 500, message = "批量删除产品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的产品id数组", type = "Long[]") Long[] ids){
        return apiGoodsFeign.delete(ids);
    }

    /**
     * @Description 产品管理之修改回显
     * @Author  jackhu
     * @Date 9/25/2019 12:05 PM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "产品管理之修改回显",notes = "接受对象参数brandId")
    @RequestMapping(value = "/edit/{id}",method =  RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改回显产品成功"),
            @ApiResponse(code = 500, message = "修改回显产品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult updateEdit(@PathVariable @ApiParam(name = "产品id") Long id){
        return apiGoodsFeign.updateEdit(id);
    }

    /**
     * @Description 根据关键子模糊查询产品
     * @Author  jackhu
     * @Date 9/25/2019 12:05 PM
     * @Param [goods]
     * @Return com.supergo.http.HttpResult
     * @Exception   
     */
    @ApiOperation(value = "根据关键子模糊查询产品",notes = "接受对象参数goods")
    @RequestMapping(value = "/findByWhere",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "模糊查询产品成功"),
            @ApiResponse(code = 500, message = "模糊查询产品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findByWhere(@RequestBody(required = false) @ApiParam(name = "产品对象") Goods goods){
        return apiGoodsFeign.findByWhere(goods);
    }

}
