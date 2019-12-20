package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.GoodsDescService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.GoodsDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：商品描述增删改查
 * @Param 
 * @Return 
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 16:54
*/
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:54
    */
    @Autowired
    private GoodsDescService goodsDescService;

    /**
     * 功能描述：添加商品描述
     * @Param [goodsDesc]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:55
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) GoodsDesc goodsDesc) {
        try {
            goodsDescService.add(goodsDesc);
            return HttpResult.ok("添加商品详情成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加商品详情失败!");
    }

    /**
     * 功能描述：删除商品描述
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:55
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids) {
        try {
            goodsDescService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改商品描述
     * @Param [goodsDesc]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:55
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) GoodsDesc goodsDesc) {
        try {
            goodsDescService.update(goodsDesc);
            return HttpResult.ok("修改商品详情成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改商品详情失败!");
    }

    /**
     * 功能描述：根据ID查询商品描述
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:55
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(goodsDescService.findOne(id));
    }


    /**
     * 功能描述：根据分页查询商品描述
     * @Param [pageNum, size, goodsDesc]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:56
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) GoodsDesc goodsDesc) {
        try {
            //分页查询
            PageResult result = goodsDescService.findPage(pageNum, size, goodsDesc);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
