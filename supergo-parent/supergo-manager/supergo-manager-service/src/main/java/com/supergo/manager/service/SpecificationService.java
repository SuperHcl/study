package com.supergo.manager.service;

import com.supergo.common.pojo.Specification;
import com.supergo.page.PageResult;
import com.supergo.service.base.BaseService;

import java.util.List;
import java.util.Map;
/**
 * 功能描述：规格service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 17:02
*/
public interface SpecificationService extends BaseService<Specification>{

    List<Map<String, Object>> specificationList();

    int addSpecification(Specification specification);

    int deleteSpecification(List<Long> ids);

    int updateSpecification(Specification specification);

    Specification getBySpecificationId(Long id);

    PageResult getAllSpecification(Integer page, Integer rows, Specification specification);

}
