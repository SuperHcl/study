package com.supergo.manager.mapper;

import com.supergo.common.pojo.SpecificationOption;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @ClassName SpecificationOptionMapper
 * @Description TODO
 * @Author wesker
 * @Date 6/4/2019 11:17 AM
 * @Version 1.0
 **/
// @org.apache.ibatis.annotations.Mapper
public interface SpecificationOptionMapper extends Mapper<SpecificationOption>, InsertListMapper<SpecificationOption> {
}
