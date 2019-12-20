package com.supergo.feign;

import com.supergo.common.pojo.Goods;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiGoodsFeign {

    /**
     * 需求：查询所有
     * @return
     */
    @RequestMapping("/goods/findAll")
    HttpResult findAll();

    /**
     * 需求：分页查询
     * 请求方式：GET
     * 参数：page,rows
     * @return
     */
    @RequestMapping(value = "/goods/query/{page}/{rows}",method = RequestMethod.GET)
    HttpResult findPage(@RequestBody(required=false) Goods goods, @PathVariable("page") Integer page, @PathVariable("rows") Integer rows);

    /**
     * 品牌管理之新建
     * @author 范江浩
     */
    @RequestMapping("/goods/saveOrUpdate")
    HttpResult saveOrUpdate(@RequestBody(required=false) Goods goods);

    /**
     * 品牌管理之删除
     * @author 范江浩
     */
    @RequestMapping("/goods/delete")
    HttpResult delete(@RequestBody(required=false) Long[] ids);

    /**
     * 品牌管理之修改回显
     */
    @RequestMapping("/goods/edit/{id}")
    HttpResult updateEdit(@PathVariable("id") Long id);

    /**
     * 根据关键子模糊查询品牌
     *
     * */
    @RequestMapping("/goods/findByWhere")
    HttpResult findByWhere(@RequestBody(required=false) Goods goods);


    @RequestMapping("/goods/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/goods/updateStatus")
    HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status);

    @RequestMapping("/goods/querySpecificationOption")
    HttpResult querySpecificationOption(@RequestParam("typeTemplateId") Long typeTemplateId);

}
