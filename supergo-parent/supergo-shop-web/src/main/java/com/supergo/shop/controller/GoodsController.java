package com.supergo.shop.controller;

import com.supergo.common.pojo.Goods;
import com.supergo.feign.ApiGoodsFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  运营商商品信息增删改查
 *
 * @param:
 * @return:
 * @auther: wesker
 * @date: 6/17/2019 9:37 AM
 */
@RestController
@RequestMapping(value = "/goods")
@Api(value = "运营商商品信息增删改查表现层", protocols = "http", description = "运营商商品信息增删改查表现层")
public class GoodsController {

    /**
     * 功能描述: 运营商商品调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiGoodsFeign apiGoodsFeign;

    /**
     * 功能描述: 增加Goods
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加Goods", notes = "接收Goods,新增Goods信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增Goods成功"),
            @ApiResponse(code = 500, message = "新增Goods失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "Goods信息", value = "传入json格式", required = false) Goods goods) {
        //设置商家ID
        // String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId("123");
        //状态  未审核
        goods.setAuditStatus("0");
        return apiGoodsFeign.saveOrUpdate(goods);
    }


    /**
     * 功能描述: 删除操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除Goods", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除Goods成功"),
            @ApiResponse(code = 500, message = "批量删除Goods失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的Goods id数组", type = "Long[]") Long[] ids) {
        return apiGoodsFeign.delete(ids);
    }

    /**
     * 功能描述: 修改操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改Goods", notes = "接收Goods信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改Goods成功"),
            @ApiResponse(code = 500, message = "修改Goods失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "Goods信息", value = "传入json格式", required = false) Goods goods) {
        //识别用户修改的商品是否属于自己的商品
        String sellerId = "yijia";
        if (!goods.getSellerId().equals(sellerId)) {
            return HttpResult.error("非法操作！");
        }
        //修改
        return apiGoodsFeign.saveOrUpdate(goods);
    }

    /**
     * 功能描述: 根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询Goods", notes = "接收Goods id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "Goods id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询Goods成功"),
            @ApiResponse(code = 500, message = "根据id查询Goods失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") Long id) {
        return apiGoodsFeign.getById(id);
    }


    /**
     * 功能描述: 集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/query/{page}/{rows}", method = RequestMethod.POST)
    @ApiOperation(value = "Goods分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询Goods成功"),
            @ApiResponse(code = 500, message = "条件分页查询Goods失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@PathVariable("page") Integer page,
                           @PathVariable("rows") Integer rows,
                           @RequestBody(required = false) @ApiParam(name = "Goods对象") Goods goods) {
        //获取商家ID
        // String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        String sellerId = "yijia";
        goods.setSellerId(sellerId);
        return apiGoodsFeign.findPage(goods, page, rows);
    }

    @RequestMapping("/updateStatus")
    public HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status) {
        return apiGoodsFeign.updateStatus(ids, status);
    }

    @RequestMapping("/querySpecificationOption/{typeTemplateId}")
    public HttpResult querySpecificationOption(@PathVariable("typeTemplateId") Long typeTemplateId) {
        return apiGoodsFeign.querySpecificationOption(typeTemplateId);
    }

}
