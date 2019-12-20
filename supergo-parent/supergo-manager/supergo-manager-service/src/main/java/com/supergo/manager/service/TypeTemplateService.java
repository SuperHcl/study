package com.supergo.manager.service;
import com.supergo.common.pojo.TbAttribute;
import com.supergo.common.pojo.TbTypeTemplate;
import com.supergo.page.PageResult;
import com.supergo.service.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：类型模板service
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 17:03
*/
public interface TypeTemplateService extends BaseService<TbTypeTemplate> {

    List<Map> findSpecOptionsList(Long typeId);

    List<Map> getSpecificationOptionById(Long typeTemplateId);

    PageResult getAllTemplate(Integer page, Integer rows, TbTypeTemplate tbTypeTemplate);

    List<TbAttribute> getAllAttrNotPage();

}
