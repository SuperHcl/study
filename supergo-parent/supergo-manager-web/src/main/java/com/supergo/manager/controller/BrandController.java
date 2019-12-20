package com.supergo.manager.controller;

import com.supergo.common.pojo.Brand;
import com.supergo.feign.ApiBrandFeign;
import com.supergo.http.HttpResult;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description 品牌增删改查
 * @Author  jackhu
 * @Date 9/25/2019 10:20 AM
 * @Param
 * @Return
 * @Exception
 */
@Api(value = "品牌控制器", protocols = "http", description = "品牌控制器")
@RestController
@RequestMapping("/brand")
public class BrandController {

    /**
     * @Description 注入品牌service对象
     * @Author  jackhu
     * @Date 9/25/2019 10:20 AM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiBrandFeign apiBrandFeign;

    /**
     * @Description 查询所有品牌数据
     * @Author  jackhu
     * @Date 9/25/2019 10:21 AM
     * @Param []
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "查询所有品牌数据")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询品牌成功"),
            @ApiResponse(code = 500, message = "查询品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findAll(){
        HttpResult result = apiBrandFeign.findAll();
        return result;
    }

    /**
     * @Description 品牌管理之分页查询
     * @Author  jackhu
     * @Date 9/25/2019 10:21 AM
     * @Param [brand, page, rows]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "品牌分页查询",notes = "接受分页参数page,rows")
    @RequestMapping(value = "/query/{page}/{rows}",method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询品牌成功"),
            @ApiResponse(code = 500, message = "条件分页查询品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult queryBrand(@RequestBody(required = false) @ApiParam(name = "品牌对象") Brand brand, @PathVariable Integer page, @PathVariable Integer rows){
        //分页查询
        return apiBrandFeign.findPage(brand,page,rows);
    }

    /**
     * @Description 品牌管理之新建
     * @Author  jackhu
     * @Date 9/25/2019 10:21 AM
     * @Param [brand]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "品牌管理之新建",notes = "接受对象参数brand")
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增品牌成功"),
            @ApiResponse(code = 500, message = "新增品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult saveOrUpdate(@RequestBody(required = false) @ApiParam(name = "品牌对象", value = "传入json格式", required = false) Brand brand){
        return  apiBrandFeign.saveOrUpdate(brand);
    }

    /**
     * 功能描述: 品牌管理之删除
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:43 AM
     */
    @ApiOperation(value = "品牌管理之删除",notes = "接受对象参数ids")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除品牌成功"),
            @ApiResponse(code = 500, message = "批量删除品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的品牌id数组", type = "Integer[]") Integer[] ids){
        return apiBrandFeign.delete(ids);
    }

    /**
     * @Description  品牌管理之修改回显
     * @Author  jackhu
     * @Date 9/25/2019 10:22 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "品牌管理之修改回显",notes = "接受对象参数brandId")
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改回显品牌成功"),
            @ApiResponse(code = 500, message = "修改回显品牌失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult updateEdit(@PathVariable Integer id){
        return apiBrandFeign.updateEdit(id);
    }

    /**
     * @Description 根据关键子模糊查询品牌
     * @Author  jackhu
     * @Date 9/25/2019 10:22 AM
     * @Param [brand]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @ApiOperation(value = "根据关键子模糊查询品牌",notes = "接受对象参数brand")
    @RequestMapping(value = "/findByWhere",method = RequestMethod.POST)
    @ApiResponses({
            @ApiResponse(code = 200, message = "模糊查询品牌成功"),
            @ApiResponse(code = 500, message = "模糊查询品牌败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult findByWhere(@RequestBody(required = false) @ApiParam(name = "品牌对象") Brand brand){
        return apiBrandFeign.findByWhere(brand);
    }

}
