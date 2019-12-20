package com.supergo.manager.controller;

import com.supergo.feign.ApiSeckillGoodsFeign;
import com.supergo.http.HttpResult;

import com.supergo.common.pojo.SeckillGoods;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述:  秒杀息增删改查
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping(value = "/seckillGoods")
@Api(value = "秒杀息增删改查表现层", protocols = "http", description = "秒杀息增删改查表现层")
public class SeckillGoodsController {

    /**
     * 功能描述: 秒杀调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiSeckillGoodsFeign apiSeckillGoodsFeign;

    /***
     * 秒杀商品审核
     * @return
     */
    @RequestMapping(value = "/update/status",method = RequestMethod.POST)
    @ApiOperation(value = "秒杀商品", notes = "接收秒杀商品，新增秒杀商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "审核状态",dataType = "String")

    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增秒杀商品成功"),
            @ApiResponse(code = 500, message = "新增秒杀商品失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status){
        return apiSeckillGoodsFeign.updateStatus(ids, status);
    }


    /***
     * 增加SeckillGoods
     * @param seckillGoods
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增秒杀", notes = "接收秒杀，新增秒杀信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增秒杀成功"),
            @ApiResponse(code = 500, message = "新增秒杀失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "秒杀信息", value = "传入json格式", required = false) SeckillGoods seckillGoods){
        return apiSeckillGoodsFeign.add(seckillGoods);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除秒杀", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除秒杀成功"),
            @ApiResponse(code = 500, message = "批量删除秒杀失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的地址id数组", type = "Long[]") Long[] ids){
        return apiSeckillGoodsFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param seckillGoods
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改秒杀", notes = "接收秒杀信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改秒杀成功"),
            @ApiResponse(code = 500, message = "修改秒杀失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "秒杀信息", value = "传入json格式", required = false) SeckillGoods seckillGoods){
        return apiSeckillGoodsFeign.update(seckillGoods);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询秒杀", notes = "接收秒杀id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "秒杀id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询秒杀成功"),
            @ApiResponse(code = 500, message = "根据id查询秒杀失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiSeckillGoodsFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ApiOperation(value = "秒杀分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询秒杀成功"),
            @ApiResponse(code = 500, message = "条件分页查询秒杀失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult list(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                           @RequestBody(required = false) @ApiParam(name = "秒杀对象") SeckillGoods seckillGoods) {
        return apiSeckillGoodsFeign.getAll(pageNum,size,seckillGoods);
    }

}
