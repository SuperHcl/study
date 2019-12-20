package com.supergo.portal.controller;

import com.supergo.common.pojo.Content;
import com.supergo.feign.ApiContentFeign;
import com.supergo.http.HttpResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *
 * @Author:jackhu
 * @Description:Content的增删改查
 * @date: ${date}
 *
 ****/
@RestController
@RequestMapping(value = "/content")
public class ContentController {

    @Autowired
    private ApiContentFeign apiContentFeign;

    /****
     * 根据分类ID查询广告
     * URL: /{categoryId}/list
     */
    @RequestMapping(value = "/{categoryId}/list")
    public HttpResult categoryContentList(@PathVariable(value = "categoryId")Long categoryId){
        //调用Service查询
        return apiContentFeign.categoryContentList(categoryId);
    }

    /***
     * 增加Content
     * @param content
     * @return
     */
    @RequestMapping(value = "/add")
    public HttpResult add(@RequestBody(required = false) Content content){
        return apiContentFeign.add(content);
    }


    /***
     * 删除操作
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        return apiContentFeign.delete(ids);
    }

    /***
     * 修改操作
     * @param content
     * @return
     */
    @RequestMapping(value = "/update")
    public HttpResult update(@RequestBody(required = false) Content content){
        return apiContentFeign.update(content);
    }

    /***
     * 根据ID查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}")
    public HttpResult getById(@PathVariable(value = "id")Long id){
        return apiContentFeign.getById(id);
    }

    /***
     * 集合查询
     * @return
     */
    @RequestMapping(value = "/getAll")
    public HttpResult getAll(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "size",required = false,defaultValue = "10")Integer size,
                                @RequestBody Content content) {
        return apiContentFeign.getAll(pageNum,size,content);
    }

}
