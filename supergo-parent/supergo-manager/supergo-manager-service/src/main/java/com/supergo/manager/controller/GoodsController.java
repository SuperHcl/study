package com.supergo.manager.controller;

import com.supergo.common.pojo.Goods;
import com.supergo.manager.service.GoodsService;
import com.supergo.http.HttpResult;
import com.supergo.page.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 功能描述：产品增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 16:51
*/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:51
    */
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/updateStatus")
    public HttpResult updateStatus(@RequestBody Long[] ids,@RequestParam("status") String status){
        int count = goodsService.updateStatus(Arrays.asList(ids), status);
        return HttpResult.ok(count);
    }
    /**
     * 功能描述：查询所有产品数据
     * @Param []
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:52
    */
    @RequestMapping("/findAll")
    public HttpResult findAll(){
        HttpResult result = goodsService.findAll();
        return result;
    }


    /**
     * 功能描述：产品分类管理之分页查询
     * @Param [goods, page, rows]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:52
    */
    @RequestMapping(value = "/query/{page}/{rows}",method = RequestMethod.POST)
    public HttpResult findPage(@RequestBody(required=false) Goods goods, @PathVariable Integer page, @PathVariable Integer rows){
        try {
            //分页查询
            PageResult result = goodsService.getAllGoods(page,rows,goods);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
    /**
     * 功能描述：产品分类管理之新建
     * @Param [goods]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:53
    */
    @RequestMapping("/saveOrUpdate")
    public HttpResult saveOrUpdate(@RequestBody(required=false) Goods goods){
        try {
            //判断是添加还是修改
            if(goods.getId()!=null){
                goodsService.updateGoods(goods);
            }else{
                //添加
                goodsService.addGoods(goods);
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
     * 功能描述：产品分类管理之删除
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:53
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody(required=false) Long[] ids){
        try {
            goodsService.deleteGoods(Arrays.asList(ids));
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }

    }
    /**
     * 功能描述：产品分类管理之修改回显
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:53
    */
    @RequestMapping("/edit/{id}")
    public HttpResult updateEdit(@PathVariable Long id){
        return HttpResult.ok(goodsService.getByGoodsId(id));
    }


    /**
     * 功能描述：根据ID查询商品
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:53
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(goodsService.getByGoodsId(id));
    }

    /**
     * 功能描述：根据关键子模糊查询商品分类
     * @Param [goods]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:54
    */
    @RequestMapping("/findByWhere")
    public HttpResult findByWhere(@RequestBody(required=false) Goods goods){
        return HttpResult.ok(goodsService.findByWhere(goods));
    }

    @RequestMapping("/querySpecificationOption")
    public HttpResult querySpecificationOption(@RequestParam("typeTemplateId") Long typeTemplateId){
        Map<String, Object> map = goodsService.querySpecificationOption(typeTemplateId);
        return HttpResult.ok(map);
    }

}
