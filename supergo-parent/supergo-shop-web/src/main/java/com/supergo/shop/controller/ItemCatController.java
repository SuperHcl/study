package com.supergo.shop.controller;

import com.supergo.common.pojo.TbItemCat;
import com.supergo.feign.ApiItemCatFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:商品类目结构
 *
 * @param:
 * @return:
 * @auther: wesker
 * @date: 6/6/2019 1:25 PM
 */
@RestController
@RequestMapping(value = "/itemCat")
@Api(value = "商品类目结构增删改查表现层", protocols = "http", description = "商品类目结构增删改查表现层")
public class ItemCatController {

    /**
     * 功能描述: 商品类目结构调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiItemCatFeign apiTbItemCatFeign;

    /**
     * 功能描述:查询所以商品类目结构
     * @auther: wesker
     * @date: 6/6/2019 1:25 PM
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "查询所以商品类目结构", notes = "查询所以商品类目结构")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询所以商品类目结构成功"),
            @ApiResponse(code = 500, message = "查询所以商品类目结构失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list() {
        return apiTbItemCatFeign.all();
    }

    /**
     * 功能描述:查询一级的类目
     *
     * @auther: wesker
     * @date: 6/6/2019 1:25 PM
     */
    @RequestMapping(value = "/parent/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "查询一级的类目", notes = "查询一级的类目")
    @ApiImplicitParam(paramType = "path", name = "id", value = "一级的类目id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询一级的类目成功"),
            @ApiResponse(code = 500, message = "根据id查询一级的类目失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getByParentId(@PathVariable(value = "id") Long id) {
        //调用Service查询
        return apiTbItemCatFeign.getByParentId(id);
    }

    /**
     * 功能描述:增加操作
     *
     * @auther: wesker
     * @date: 6/6/2019 1:25 PM
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品类目结构", notes = "接收商品类目结构，新增地址信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增商品类目结构成功"),
            @ApiResponse(code = 500, message = "新增商品类目结构失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "商品类目结构信息", value = "传入json格式", required = false) TbItemCat itemCat) {
        return apiTbItemCatFeign.add(itemCat);
    }


    /**
     * 功能描述:删除操作
     *
     * @auther: wesker
     * @date: 6/6/2019 1:25 PM
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除商品类目结构", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除商品类目结构成功"),
            @ApiResponse(code = 500, message = "批量删除商品类目结构失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的商品类目结构id数组", type = "Long[]") Long[] ids) {
        return apiTbItemCatFeign.delete(ids);
    }

    /**
     * 功能描述:修改操作
     *
     * @auther: wesker
     * @date: 6/6/2019 1:25 PM
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改商品类目结构", notes = "接收商品类目结构信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改商品类目结构成功"),
            @ApiResponse(code = 500, message = "修改商品类目结构失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "商品类目结构信息", value = "传入json格式", required = false) TbItemCat itemCat) {
        return apiTbItemCatFeign.update(itemCat);
    }

    /**
     * 功能描述: 根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询商品类目结构", notes = "接收商品类目结构id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "地址id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询商品类目结构成功"),
            @ApiResponse(code = 500, message = "根据id查询商品类目结构失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiTbItemCatFeign.getById(id);
    }

    /**
     * 功能描述: 集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 15:10 PM
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiOperation(value = "商品类目结构分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询商品类目结构成功"),
            @ApiResponse(code = 500, message = "条件分页查询商品类目结构失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                           @RequestBody(required = false) @ApiParam(name = "商品类目结构对象") TbItemCat itemCat) {
        return apiTbItemCatFeign.getAll(pageNum, size, itemCat);
    }
}
