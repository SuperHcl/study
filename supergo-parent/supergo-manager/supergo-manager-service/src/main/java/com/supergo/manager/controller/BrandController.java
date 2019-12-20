package com.supergo.manager.controller;

import com.supergo.manager.service.BrandService;
import com.supergo.page.PageResult;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Brand;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @Description 品牌信息增删改查Controller层
 * @Author jackhu
 * @Date 8/1/2019 9:52 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/brand")
public class BrandController {


    @Resource(type = BrandService.class)
    private BrandService brandService;

    /**
     * @Description 查询搜索品牌信息
     * @Author jackhu
     * @Date 8/1/2019 9:53 AM
     * @Param []
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public HttpResult findAll() {
        return brandService.findAll();
    }

    /**
     * @Description 分页查询所有品牌信息
     * @Author jackhu
     * @Date 8/1/2019 9:54 AM
     * @Param [brand, page, rows]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/query/{page}/{rows}", method = RequestMethod.POST)
    public HttpResult findPage(@RequestBody(required = false) Brand brand, @PathVariable Integer page, @PathVariable Integer rows) {
        try {
            //分页查询
            PageResult result = brandService.findPage(page, rows, brand);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 添加或修改品牌信息
     * @Author jackhu
     * @Date 8/1/2019 10:00 AM
     * @Param [brand]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public HttpResult saveOrUpdate(@RequestBody(required = false) @ApiParam(value = "Brand 对象") Brand brand) {
        try {
            //判断是添加还是修改
            if (brand.getId() != null) {
                brandService.update(brand);
            } else {
                //添加
                brandService.add(brand);
            }
            //添加修改成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //添加异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 批量删除品牌信息
     * @Author jackhu
     * @Date 8/1/2019 10:01 AM
     * @Param [ids]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HttpResult delete(@RequestBody(required = false) Integer[] ids) {
        try {
            brandService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * @Description 根据id查询品牌信息(修改回显)
     * @Author jackhu
     * @Date 8/1/2019 10:05 AM
     * @Param [brandId]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/edit/{brandId}", method = RequestMethod.GET)
    public HttpResult updateEdit(@PathVariable Integer brandId) {
        return HttpResult.ok(brandService.findOne(brandId));
    }

    /**
     * @Description 模糊查询品牌信息
     * @Author jackhu
     * @Date 8/1/2019 10:06 AM
     * @Param [brand]
     * @Return com.supergo.http.HttpResult
     * @Exception
     */
    @RequestMapping(value = "/findByWhere", method = RequestMethod.POST)
    public HttpResult findByWhere(@RequestBody(required = false) @ApiParam(value = "Brand 对象") Brand brand) {
        return HttpResult.ok(brandService.findByWhere(brand));
    }
}
