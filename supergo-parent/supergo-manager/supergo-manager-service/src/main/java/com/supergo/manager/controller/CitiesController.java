package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.CitiesService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Cities;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description 城市信息增删改查
 * @Author jackhu
 * @Date 8/1/2019 10:13 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/cities")
public class CitiesController {


    @Resource(type = CitiesService.class)
    private CitiesService citiesService;

    /**
     * @Description 添加城市信息
     * @Author jackhu
     * @Date 8/1/2019 10:10 AM
     * @Param [cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HttpResult add(@RequestBody(required = false) @ApiParam(value = "Cities 对象") Cities cities) {
        try {
            citiesService.add(cities);
            return HttpResult.ok("添加城市成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加城市失败!");
    }

    /**
     * @Description 批量删除城市信息
     * @Author jackhu
     * @Date 8/1/2019 10:13 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpResult delete(@RequestBody Integer[] ids) {
        try {
            citiesService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 修改城市信息
     * * @Author  jackhu
     * @Date 8/1/2019 10:16 AM
     * @Param [cities]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public HttpResult update(@RequestBody(required = false) @ApiParam(value = "Cities 对象") Cities cities) {
        try {
            citiesService.update(cities);
            return HttpResult.ok("修改城市成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改城市失败!");
    }

    /**
     * @Description 根据id获取城市信息
     * @Author jackhu
     * @Date 8/1/2019 10:18 AM
     * @Param [id]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(citiesService.findOne(id));
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) @ApiParam(value = "Cities 对象") Cities cities) {
        try {
            //分页查询
            PageResult result = citiesService.findPage(pageNum, size, cities);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
