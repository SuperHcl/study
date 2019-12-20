package com.supergo.feign;

import com.supergo.common.pojo.Address;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 功能描述:
 *
 * @param:
 * @return:
 * @auther: jackhu
 * @date: 6/6/2019 3:36 PM
 */
@FeignClient("jmyp-user")
public interface ApiAddressFeign {

    /**
     *
     * 功能描述: 
     *
     * @param: 
     * @return: 
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/address/getByUserName")
    HttpResult getByUserName(@RequestParam("name") String name);

    /**
     *
     * 功能描述: 
     *
     * @param: 
     * @return: 
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/address/add")
    HttpResult add(@RequestBody(required = false) Address address);

    /**
     *
     * 功能描述: 
     *
     * @param: 
     * @return: 
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/address/delete")
    HttpResult delete(@RequestBody(required = false) Long[] ids);

    /**
     *
     * 功能描述: 
     *
     * @param: 
     * @return: 
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/address/update")
    HttpResult update(@RequestBody(required = false) Address address);

    /**
     *
     * 功能描述: 
     *
     * @param:
     * @return: 
     * @auther: wesker
     * @date: 6/6/2019 3:39 PM
     */
    @RequestMapping("/address/getById")
    HttpResult getById(@RequestParam("id") Long id);

}
