package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.AddressService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Address;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 地址信息增删改查
 * @Author jackhu
 * @Date 2019/7/31 21:01
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/address")
public class AddressController {


    @Resource(type = AddressService.class)
    private AddressService addressService;

    /**
     * @Description 新增地址信息
     * @Author jackhu
     * @Date 2019/7/31 21:01
     * @Param [address]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HttpResult add(@RequestBody(required = false) @ApiParam(value = "地址对象") Address address) {
        try {
            addressService.add(address);
            return HttpResult.ok("添加地址成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加地址失败!");
    }

    /**
     * @Description 批量删除地址信息
     * @Author jackhu
     * @Date 2019/7/31 21:25
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpResult delete(@RequestBody(required = false) Long[] ids) {
        try {
            addressService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 修改地址信息
     * @Author jackhu
     * @Date 2019/7/31 21:26
     * @Param [address]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody(required = false) @ApiParam(value = "地址信息对象") Address address) {
        try {
            addressService.update(address);
            return HttpResult.ok("修改地址成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改地址失败!");
    }

    /**
     * @Description 根据id查询地址信息
     * @Author jackhu
     * @Date 2019/7/31 21:27
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(addressService.findOne(id));
    }

    /**
     * @Description 条件分页查询
     * @Author jackhu
     * @Date 2019/7/31 21:29
     * @Param [pageNum, size, address]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) @ApiParam(value = "搜索地址信息条件") Address address) {
        try {
            //分页查询
            PageResult result = addressService.findPage(pageNum, size, address);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
