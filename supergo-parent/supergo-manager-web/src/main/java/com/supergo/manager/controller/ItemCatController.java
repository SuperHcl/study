package com.supergo.manager.controller;

import com.supergo.common.pojo.TbItemCat;
import com.supergo.feign.ApiItemCatFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 商品分类信息增删改查
 * @Author jackhu
 * @Date 9/25/2019 2:17 PM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping(value = "/itemCat")
@Api(value = "商品分类信息增删改查表现层", protocols = "http", description = "商品分类信息增删改查表现层")
public class ItemCatController {

    /**
     * @Description 商品分类信息接口
     * @Author jackhu
     * @Date 9/25/2019 2:17 PM
     * @Param
     * @Return
     * @Exception
     */
    @Autowired
    private ApiItemCatFeign apiItemCatFeign;

    /**
     * @Description 查询所有商品分类信息
     * @Author jackhu
     * @Date 9/25/2019 2:18 PM
     * @Param []
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有商品分类信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询商品分类成功"),
            @ApiResponse(code = 500, message = "查询商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list() {
        return apiItemCatFeign.all();
    }

    /**
     * @Description 根据父节点ID查询商品分类信息
     * @Author jackhu
     * @Date 9/25/2019 2:19 PM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/parent/{id}")
    @ApiOperation(value = "根据父节点ID查询商品分类信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询商品分类成功"),
            @ApiResponse(code = 500, message = "查询商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getByParentId(@PathVariable(value = "id") Long id) {
        //调用Service查询
        return apiItemCatFeign.getByParentId(id);
    }

    /**
     * @Description 新增商品分类
     * @Author jackhu
     * @Date 9/25/2019 2:24 PM
     * @Param [itemCat]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品分类", notes = "接收商品分类，新增商品分类信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增商品分类成功"),
            @ApiResponse(code = 500, message = "新增商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "商品分类信息", value = "传入json格式", required = false) TbItemCat itemCat) {
        return apiItemCatFeign.add(itemCat);
    }


    /**
     * @Description 根据id删除商品分类
     * @Author jackhu
     * @Date 9/25/2019 2:24 PM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除商品分类", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除商品分类成功"),
            @ApiResponse(code = 500, message = "批量删除商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除商品分类的id数组", type = "Long[]") Long[] ids) {
        return apiItemCatFeign.delete(ids);
    }

    /**
     * @Description 修改商品分类
     * @Author jackhu
     * @Date 9/25/2019 2:24 PM
     * @Param [itemCat]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改商品分类", notes = "接收商品分类信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改商品分类成功"),
            @ApiResponse(code = 500, message = "修改商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "商品分类信息", value = "传入json格式", required = false) TbItemCat itemCat) {
        return apiItemCatFeign.update(itemCat);
    }

    /**
     * @Description 根据id查询商品分类
     * @Author jackhu
     * @Date 9/25/2019 2:23 PM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询商品分类", notes = "接收商品分类id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "商品分类id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询商品分类成功"),
            @ApiResponse(code = 500, message = "根据id查询商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiItemCatFeign.getById(id);
    }

    /**
     * @Description 商品分类分页查询
     * @Author jackhu
     * @Date 9/25/2019 2:23 PM
     * @Param [pageNum, size, itemCat]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "商品分类分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询商品分类成功"),
            @ApiResponse(code = 500, message = "条件分页查询商品分类失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "商品分类对象") TbItemCat itemCat) {
        return apiItemCatFeign.getAll(pageNum, size, itemCat);
    }


    /**
     * @Description 分页查询商品分类子节点
     * @Author  jackhu
     * @Date 9/25/2019 2:43 PM
     * @Param [page, rows, id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping("/getByParentIdAndPage/{page}/{rows}/{id}")
    @ApiOperation(value = "分页查询商品分类子节点")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "分页查询商品分类子节点成功"),
            @ApiResponse(code = 500, message = "分页查询商品分类子节点失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getByParentIdAndPage(@PathVariable("page") Integer page, @PathVariable("rows") Integer rows, @PathVariable("id") Long id) {
        return apiItemCatFeign.getByParentIdAndPage(page, rows, id);
    }

}
