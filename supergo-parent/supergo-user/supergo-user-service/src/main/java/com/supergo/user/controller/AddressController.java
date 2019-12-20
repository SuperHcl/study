package com.supergo.user.controller;

import com.supergo.http.HttpResult;
import com.supergo.common.pojo.Address;
import com.supergo.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName AddressController
 * @Description TODO
 * @Author wesker
 * @Date 6/6/2019 3:44 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/getByUserName")
    public HttpResult getByUserName(@RequestParam("name") String name){
        Address address = new Address();
        address.setUserId(name);
        List<Address> addresses = addressService.findByWhere(address);
        return HttpResult.ok(addresses);
    }

    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Address address){
        try {
            addressService.add(address);
            return HttpResult.ok("添加地址成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加地址失败!");
    }

    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody(required = false) Long[] ids){
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
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Address address){
        try {
            addressService.update(address);
            return HttpResult.ok("修改地址成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改地址失败!");
    }

    /**
     *
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(addressService.findOne(id));
    }
}
