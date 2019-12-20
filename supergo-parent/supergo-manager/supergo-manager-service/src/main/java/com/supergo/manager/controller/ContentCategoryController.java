package com.supergo.manager.controller;
import com.supergo.common.pojo.ContentCategory;
import com.supergo.http.HttpResult;
import com.supergo.manager.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能描述：分类增删改查
 * @Param 
 * @Return 
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 16:42
*/
@RestController
@RequestMapping("/categorys")
public class ContentCategoryController {

    /**
     * 功能描述：注入分类接口
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:42
    */
    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 功能描述：查询分类
     * @Param []
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:43
    */
    @RequestMapping("/all")
    public HttpResult list(){
        return contentCategoryService.findAll();
    }

    /**
     * 功能描述：添加分类
     * @Param [contentCategory]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:43
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) ContentCategory contentCategory){
        try {
            contentCategoryService.add(contentCategory);
            return HttpResult.ok("添加成功!");
        } catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.error("添加失败!");
    }

    /**
     * 功能描述：删除分类
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:43
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Integer[] ids){
        try {
            contentCategoryService.deleteByIds(ids);
            return HttpResult.ok("删除成功!");
        } catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.error("删除失败!");
    }

    /**
     * 功能描述：修改分类
     * @Param [contentCategory]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:43
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody ContentCategory contentCategory){
        try {
            contentCategoryService.update(contentCategory);
            return HttpResult.ok("修改成功!");
        } catch (Exception e){
            e.printStackTrace();
        }
        return HttpResult.error("修改失败!");
    }

    /**
     * 功能描述：根据ID查询分类
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:44
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Integer id){
        return HttpResult.ok(contentCategoryService.findOne(id));
    }

    /**
     * 功能描述：分类分页查询
     * @Param [pageNum, size, contentCategory]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 16:44
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) ContentCategory contentCategory){
        return HttpResult.ok(contentCategoryService.findPage(pageNum,size,contentCategory));
    }
}
