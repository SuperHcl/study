package com.supergo.manager.controller;

import com.supergo.common.pojo.TbAttribute;
import com.supergo.manager.service.AttrService;
import com.supergo.page.PageResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @Author jackhu
 * @Date 8/1/2019 9:43 AM
 * @Param
 * @Return
 * @Exception
 */
@RestController
@RequestMapping("/attr")
public class AttrController {

    @Resource(type = AttrService.class)
    private AttrService attrService;

    /**
     * @Description 根据id查询扩展属性信息
     * @Author jackhu
     * @Date 8/1/2019 9:45 AM
     * @Param [id]
     * @Return com.supergo.common.pojo.TbAttribute
     * @Exception
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public TbAttribute findAttrById(@RequestParam("id") Integer id) {
        return attrService.findOne(id);
    }

    /**
     * @Description 分页查询扩展属性
     * @Author jackhu
     * @Date 8/1/2019 9:47 AM
     * @Param [page, rows]
     * @Return com.supergo.page.PageResult
     * @Exception
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public PageResult findAllAttr(@RequestParam("page") Integer page, @RequestParam("rows") Integer rows) {
        PageResult pageResult = attrService.findPage(page, rows);
        return pageResult;
    }

    /**
     * @Description 添加扩展属性信息
     * @Author jackhu
     * @Date 8/1/2019 9:49 AM
     * @Param [attr]
     * @Return java.lang.Integer
     * @Exception
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer insertAttr(@RequestBody @ApiParam(value = "TbAttribute 对象") TbAttribute attr) {
        try {
            attrService.add(attr);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @Description 修改扩展属性信息
     * @Author jackhu
     * @Date 8/1/2019 9:51 AM
     * @Param [attr]
     * @Return java.lang.Integer
     * @Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Integer updateAttr(@RequestBody @ApiParam(value = "TbAttribute 对象") TbAttribute attr) {
        try {
            attrService.update(attr);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
