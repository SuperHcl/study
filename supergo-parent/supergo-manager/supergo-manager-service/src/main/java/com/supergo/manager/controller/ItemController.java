package com.supergo.manager.controller;

import com.supergo.common.pojo.Cart;
import com.supergo.common.pojo.Item;
import com.supergo.http.HttpResult;
import com.supergo.manager.service.ItemService;
import com.supergo.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述：商品增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:01
*/
@RestController
@RequestMapping("/item")
public class ItemController {

    /**
     * 功能描述：注入service接口
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:02
    */
    @Autowired
    private ItemService tbItemService;

    /**
     * 功能描述：根据ID查询
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:02
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(tbItemService.findOne(id));
    }

    /**
     * 功能描述：添加商品详情
     * @Param [item]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:02
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Item item){
        try {
            tbItemService.add(item);
            return HttpResult.ok("添加商品详情成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加商品详情失败!");
    }

    /**
     * 功能描述：删除商品详情
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:03
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        try {
            tbItemService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改商品详情
     * @Param [item]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:03
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Item item){
        try {
            tbItemService.update(item);
            return HttpResult.ok("修改商品详情成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改商品详情失败!");
    }

    /**
     * 功能描述：根据分页查询商品详情
     * @Param [pageNum, size, item]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:03
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Item item){
        try {
            //分页查询
            PageResult result = tbItemService.findPage(pageNum, size, item);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }

    @RequestMapping("/updateStatus")
    public HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status){
        return HttpResult.ok(tbItemService.updateStatus(ids,status));
    }


    @RequestMapping("/item/deductionStock")
    public boolean deductionStock(List<Cart> cartList){
        boolean flag = tbItemService.deductionStock(cartList);
        return flag;
    }

}
