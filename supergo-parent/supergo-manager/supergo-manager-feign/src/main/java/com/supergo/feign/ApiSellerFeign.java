package com.supergo.feign;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Seller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiSellerFeign {


    @RequestMapping("/seller/findAll")
    HttpResult findAll();

    /**
     * 需求：分页查询
     * 请求方式：GET
     * 参数：page,rows
     *
     * @return
     */
    @RequestMapping(value = "/seller/query/{page}/{rows}", method = RequestMethod.POST)
    HttpResult findPage(@RequestBody(required = false) Seller seller, @PathVariable("page") Integer page, @PathVariable("rows") Integer rows);

    /**
     * 商家管理之删除
     *
     * @author 范江浩
     */
    @RequestMapping("/seller/delete")
    HttpResult delete(@RequestBody(required = false) String[] sellerIds);

    /**
     * 根据关键子模糊查询商家
     */
    @RequestMapping("/seller/findByWhere")
    HttpResult findByWhere(@RequestBody(required = false) Seller seller);

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 5/31/2019 4:59 PM
     */
    @RequestMapping("/seller/getById")
    HttpResult getById(@RequestParam("id") String id);

    /**
     * 功能描述:  获取商家状态
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/3/2019 11:32 AM
     */
    @RequestMapping(value = "/seller/status", method = RequestMethod.GET)
    HttpResult updateStatus(@RequestParam("sellerId") String sellerId, @RequestParam("status") String status);

    /**
     * 功能描述:  增加商家
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/3/2019 2:25 PM
     */
    @RequestMapping(value = "/seller/add", method = RequestMethod.POST)
    HttpResult add(@RequestBody(required = false) Seller seller);


    /**
     * 功能描述:  修改商家
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/3/2019 2:25 PM
     */
    @RequestMapping(value = "/seller/update", method = RequestMethod.POST)
    HttpResult update(@RequestBody(required = false) Seller seller);

}
