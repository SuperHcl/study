package com.supergo.shop.controller;

import com.supergo.common.pojo.Seller;
import com.supergo.feign.ApiSellerFeign;
import com.supergo.http.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 功能描述:  店铺注册信息增删改查
 *
 * @param:
 * @return:
 * @auther: wesker
 * @date: 6/17/2019 9:37 AM
 */
@RestController
@RequestMapping(value = "/seller")
public class SellerController {

    /**
     * 功能描述: 店铺注册调用接口
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @Autowired
    private ApiSellerFeign apiSellerFeign;

    // @Autowired
    // private BCryptPasswordEncoder encoder;

    /**
     * 功能描述:增加操作
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 6/6/2019 1:31 PM
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "增加店铺注册", notes = "接收店铺注册,新增店铺注册信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "新增店铺注册成功"),
            @ApiResponse(code = 500, message = "新增店铺注册失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult add(@RequestBody(required = false) @ApiParam(name = "店铺注册信息", value = "传入json格式", required = false) Seller seller) {
        try {
            //加密密码
            // String pwd = encoder.encode(seller.getPassword());
            String pwd = "加密后的密码";
            seller.setPassword(pwd);
            //数据补全
            seller.setStatus("0");  //0初始状态
            seller.setCreateTime(new Date());   //创建时间
            return apiSellerFeign.add(seller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加失败");
    }


    /**
     * 功能描述: 删除操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除店铺注册信息", notes = "接收数组ids")
    @ApiResponses({
            @ApiResponse(code = 200, message = "批量删除店铺注册信息成功"),
            @ApiResponse(code = 500, message = "批量删除店铺注册信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult delete(@RequestBody @ApiParam(name = "要删除的店铺注册信息 id数组", type = "Long[]") String[] ids) {
        return apiSellerFeign.delete(ids);
    }

    /**
     * 功能描述: 修改操作
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改店铺注册信息", notes = "接收店铺注册信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改店铺注册信息成功"),
            @ApiResponse(code = 500, message = "修改店铺注册信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult update(@RequestBody(required = false) @ApiParam(name = "店铺注册信息", value = "传入json格式", required = false) Seller seller) {
        return apiSellerFeign.update(seller);
    }

    /**
     * 功能描述: 根据ID查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询店铺注册信息", notes = "接收店铺注册信息 id")
    @ApiImplicitParam(paramType = "path", name = "id", value = "店铺注册信息 id", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "根据id查询店铺注册信息成功"),
            @ApiResponse(code = 500, message = "根据id查询店铺注册信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getById(@PathVariable(value = "id") String id) {
        return apiSellerFeign.getById(id);
    }

    /**
     * 功能描述: 集合查询
     *
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/getAll/{pageNum}/{size}",method = RequestMethod.POST)
    @ApiOperation(value = "店铺注册信息分页查询", notes = "接受分页参数pageNum,size")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "pageNum", value = "第几页", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "一页显示多少条数据", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "条件分页查询店铺注册信息成功"),
            @ApiResponse(code = 500, message = "条件分页查询店铺注册信息失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult getAll(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                             @RequestBody(required = false) @ApiParam(name = "店铺注册信息对象") Seller seller) {
        return apiSellerFeign.findPage(seller, pageNum, size);
    }

}
