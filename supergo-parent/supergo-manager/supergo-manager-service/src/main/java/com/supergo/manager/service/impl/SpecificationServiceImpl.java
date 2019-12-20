package com.supergo.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supergo.common.pojo.*;
import com.supergo.manager.mapper.SpecificationOptionMapper;
import com.supergo.manager.service.SpecificationService;
import com.supergo.manager.mapper.SpecificationMapper;
import com.supergo.page.PageResult;
import com.supergo.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
/**
 * 功能描述：规格service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 17:01
*/
@Service
public class SpecificationServiceImpl extends BaseServiceImpl<Specification> implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public List<Map<String, Object>> specificationList() {
        return specificationMapper.specificationList();
    }

    @Override
    public int addSpecification(Specification specification) {
        //增加规格信息
        int acount = specificationMapper.insertSelective(specification);
        //增加规格选项信息[多条记录：集合]
        for (SpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            //获取添加的规格的id值,并赋给specId属性
            specificationOption.setSpecId(specification.getId());
            //循环增加到另外一张表中
            //specificationOptionMapper.insertSelective(specificationOption);
        }
        //批量增加
        specificationOptionMapper.insertList(specification.getSpecificationOptionList());
        return acount;
    }

    @Override
    public int deleteSpecification(List<Long> ids) {
        Example example = new Example(Specification.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",ids);
        int dcount = specificationMapper.deleteByExample(example);
        //删除规格选项  spec_id  delete from tb_specification_option where spec_id in(123,232,34)
        Example exampleOption = new Example(SpecificationOption.class);
        Example.Criteria criteriaOption = exampleOption.createCriteria();
        criteriaOption.andIn("specId",ids); //id in(1,23,34)
        specificationOptionMapper.deleteByExample(exampleOption);
        return dcount;
    }

    @Override

    public int updateSpecification(Specification specification) {
        //修改规格信息
        int mcount = specificationMapper.updateByPrimaryKeySelective(specification);
        //删除之前已经拥有的规格选项 delete from tb_specification_option where spec_id=id
        SpecificationOption delspecificationOption = new SpecificationOption();
        delspecificationOption.setSpecId(specification.getId());
        specificationOptionMapper.delete(delspecificationOption);
        //批量增加规格选项
        for (SpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            //获取添加的规格的id值,并赋给specId属性
            specificationOption.setSpecId(specification.getId());
            //循环增加到另外一张表中
            //specificationOptionMapper.insertSelective(specificationOption);
        }
        specificationOptionMapper.insertList(specification.getSpecificationOptionList());
        return mcount;
    }

    @Override
    public Specification getBySpecificationId(Long id) {
        //先查询规格数据
        Specification specification = specificationMapper.selectByPrimaryKey(id);
        //查询规格选项数据  select * from tb_specification_option where spec_id=id
        SpecificationOption specificationOption = new SpecificationOption();
        specificationOption.setSpecId(id);
        List<SpecificationOption> options = specificationOptionMapper.select(specificationOption);
        specification.setSpecificationOptionList(options);
        return specification;
    }

    @Override
    public PageResult getAllSpecification(Integer page, Integer rows, Specification specification) {
        PageHelper.startPage(page,rows);
        List<Specification> specifications = specificationMapper.selectAll();
        for (Specification spec : specifications){
            SpecificationOptionExample example = new SpecificationOptionExample();
            example.createCriteria().andSpecIdEqualTo(spec.getId());
            spec.setSpecificationOptionList(specificationOptionMapper.selectByExample(example));
        }
        PageInfo<Specification> info = new PageInfo<>(specifications);
        return new PageResult(info.getTotal(),
                info.getList(),rows);
    }
}
