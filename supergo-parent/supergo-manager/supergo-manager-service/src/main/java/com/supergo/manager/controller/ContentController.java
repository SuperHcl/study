package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.manager.service.ContentService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能描述：内容增删改查
 * @Param 
 * @Return 
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 16:45
*/
@RestController
@RequestMapping("/content")
public class ContentController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/23
     * @Time 14:44
    */
    @Autowired
    private ContentService contentService;

    /**
     * 功能描述：添加内容
     * @Param [content]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:45
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) Content content) {
        try {
            contentService.add(content);
            return HttpResult.ok("添加内容成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加内容失败!");
    }

    /**
     * 功能描述：删除内容
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:45
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids) {
        try {
            contentService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：修改内容
     * @Param [content]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:46
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) Content content) {
        try {
            contentService.update(content);
            return HttpResult.ok("修改城市成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改城市失败!");
    }


    /**
     * 功能描述：根据ID查询内容
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:46
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id) {
        return HttpResult.ok(contentService.findOne(id));
    }


    /**
     * 功能描述：分页查询内容
     * @Param [pageNum, size, content]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:46
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Content content) {
        try {
            //分页查询
            PageResult result = contentService.findPage(pageNum, size, content);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }

    /**
     * 功能描述：
     * @Param [categoryId]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:50
    */
    @RequestMapping("/categoryContentList")
    public HttpResult categoryContentList(@RequestParam("categoryId") Long categoryId){
        List<Content> contents = contentService.categoryContentList(categoryId);
        return HttpResult.ok(contents);
    }

}
