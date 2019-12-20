package com.supergo.feign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.ContentCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiADCategoryFeign {


    @RequestMapping("/adcategory/findAll")
    public HttpResult findAll();

    /**
     * 需求：分页查询
     * 请求方式：GET
     * 参数：page,rows
     * @return
     */
    @RequestMapping(value = "/adcategory/query/{page}/{rows}",method = RequestMethod.POST)
    public HttpResult findPage(@RequestBody(required = false) ContentCategory adcategory, @PathVariable("page") Integer page, @PathVariable("rows") Integer rows);

    /**
     * 品牌管理之新建
     * @author 范江浩
     */
    @RequestMapping("/adcategory/saveOrUpdate")
    public HttpResult saveOrUpdate(@RequestBody(required = false) ContentCategory adcategory);


    /**
     * 品牌管理之删除
     * @author hubin
     */
    @RequestMapping("/adcategory/delete")
    public HttpResult delete(@RequestBody(required = false) Integer[] ids);

    /**
     * 品牌管理之修改回显
     */
    @RequestMapping("/adcategory/edit/{id}")
    public HttpResult updateEdit(@PathVariable("id") Integer id);

    /**
     * 根据关键子模糊查询品牌
     *
     * */
    @RequestMapping("/adcategory/findByWhere")
    public HttpResult findByWhere(@RequestBody(required = false) ContentCategory adcategory);



}
