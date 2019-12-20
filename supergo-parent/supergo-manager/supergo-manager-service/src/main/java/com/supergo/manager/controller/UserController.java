package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.UserService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：用户增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:29
*/
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:29
    */
    @Autowired
    private UserService userService;

    /**
     * 功能描述：添加用户
     * @Param [user]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:29
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) User user){
        try {
            userService.add(user);
            return HttpResult.ok("添加用户成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加用户失败!");
    }


    /**
     * 功能描述：删除用户
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:30
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        try {
            userService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改用户
     * @Param [user]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:30
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) User user){
        try {
            userService.update(user);
            return HttpResult.ok("修改用户成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改用户失败!");
    }


    /**
     * 功能描述：根据ID查询用户
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:30
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(userService.findOne(id));
    }


    /**
     * 功能描述：根据分页查询用户
     * @Param [pageNum, size, user]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:30
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) User user){
        try {
            //分页查询
            PageResult result = userService.findPage(pageNum, size, user);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}
