package com.supergo.feign;
import com.supergo.common.pojo.Specification;
import com.supergo.http.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by on 2019/3/14.
 */
@FeignClient("supergo-manager")
public interface ApiSpecificationFeign {

    /**
     * 品牌管理之删除
     * @author 范江浩
     */
    @RequestMapping("/specification/delete")
    HttpResult delete(@RequestBody(required = false) Long[] ids);

    /**
     * 根据关键子模糊查询品牌
     *
     * */
    @RequestMapping("/specification/findByWhere")
    public HttpResult findByWhere(@RequestBody(required = false) Specification specification);

    @RequestMapping("/specification/specificationList")
    HttpResult list();

    @RequestMapping("/specification/add")
    HttpResult add(@RequestBody(required = false) Specification specification);

    @RequestMapping("/specification/update")
    HttpResult update(@RequestBody(required = false) Specification specification);

    @RequestMapping("/specification/getById")
    HttpResult getById(@RequestParam("id") Long id);

    @RequestMapping("/specification/getAll")
    HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) Specification specification);

}
