package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.SellerService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Seller;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：商家增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:18
*/
@RestController
@RequestMapping("/seller")
public class SellerController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:19
    */
    @Autowired
    private SellerService sellerService;

    /**
     * 功能描述：据主键查询商家
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:19
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") String id) {
        return HttpResult.ok(sellerService.findOne(id));
    }

    /**
     * 功能描述：添加商家
     * @Param [seller]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:19
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Seller seller) {
        try {
            sellerService.add(seller);
            return HttpResult.ok("添加商家成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加商家失败!");
    }

    /**
     * 功能描述：修改商家
     * @Param [seller]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:20
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Seller seller) {
        try {
            sellerService.update(seller);
            return HttpResult.ok("修改商家成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改商家失败!");
    }

    /**
     * 功能描述：删除商家
     * @Param [sellerIds]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:21
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody(required = false) String[] sellerIds) {
        try {
            sellerService.deleteByIds(sellerIds);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：商家管理之分页查询
     * @Param [seller, page, rows]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:22
    */
    @RequestMapping(value = "/query/{page}/{rows}", method = RequestMethod.POST)
    public HttpResult findPage(@RequestBody(required = false) Seller seller, @PathVariable Integer page, @PathVariable Integer rows) {
        try {
            //分页查询
            PageResult result = sellerService.getAllSellers(page, rows, seller);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：查询商家状态
     * @Param [sellerId, status]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:22
    */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public HttpResult updateStatus(@RequestParam("sellerId") String sellerId, @RequestParam("status") String status) {
        int mcount = sellerService.updateStatus(sellerId, status);
        if (mcount > 0) {
            return HttpResult.ok("审核成功!");
        }
        return HttpResult.error("审核失败!");
    }
}