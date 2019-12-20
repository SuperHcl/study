package com.supergo.manager.mapper;

import com.supergo.common.pojo.Specification;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

// @org.apache.ibatis.annotations.Mapper
public interface SpecificationMapper extends Mapper<Specification> {

    @Select("select id,spec_name as text from tb_specification")
    List<Map<String, Object>> specificationList();

}
