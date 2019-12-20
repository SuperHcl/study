package com.supergo.feign;
import com.supergo.common.pojo.Brand;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiBrandFeign {


    @RequestMapping("/brand/findAll")
    public HttpResult findAll();

    /**
     * 需求：分页查询
     * 请求方式：GET
     * 参数：page,rows
     * @return
     */
    @RequestMapping(value = "/brand/query/{page}/{rows}",method = RequestMethod.POST)
    public HttpResult findPage(@RequestBody(required=false) Brand brand, @PathVariable("page") Integer page, @PathVariable("rows") Integer rows);

    /**
     * 品牌管理之新建
     * @author 范江浩
     */
    @RequestMapping("/brand/saveOrUpdate")
    public HttpResult saveOrUpdate(@RequestBody(required=false) Brand brand);


    /**
     * 品牌管理之删除
     * @author 范江浩
     */
    @RequestMapping("/brand/delete")
    public HttpResult delete(@RequestBody(required=false) Integer[] ids);

    /**
     * 品牌管理之修改回显
     */
    @RequestMapping("/brand/edit/{id}")
    public HttpResult updateEdit(@PathVariable("id") Integer id);

    /**
     * 根据关键子模糊查询品牌
     *
     * */
    @RequestMapping("/brand/findByWhere")
    public HttpResult findByWhere(@RequestBody(required=false) Brand brand);



}
