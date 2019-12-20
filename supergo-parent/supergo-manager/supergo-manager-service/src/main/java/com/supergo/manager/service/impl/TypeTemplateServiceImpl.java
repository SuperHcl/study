package com.supergo.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supergo.common.pojo.*;
import com.supergo.manager.mapper.AttrMapper;
import com.supergo.manager.service.TypeTemplateService;
import com.supergo.manager.mapper.SpecificationOptionMapper;
import com.supergo.manager.mapper.TypeTemplateMapper;
import com.supergo.manager.utils.RedisUtils;
import com.supergo.page.PageResult;
import com.supergo.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：类型模板service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 17:03
*/
@Service
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AttrMapper attrMapper;


    /**
     * 需求：商品录入，根据不同的规格选项组合成不同sku商品，因此查询规格选项
     *
     * @param typeId
     * @return
     */
    @Override
    public List<Map> findSpecOptionsList(Long typeId) {
        //根据模板id查询模板对象
        TbTypeTemplate template = typeTemplateMapper.selectByPrimaryKey(typeId);
        //获取模板中规格属性
        //[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        String specIds = template.getSpecIds();
        //把规格json字符串转换为json对象
        List<Map> specMap = JSON.parseArray(specIds, Map.class);
        //循环规格集合
        for (Map map : specMap) {
            //获取map的id
            Long id = new Long((Integer) map.get("id"));
            //创建规格选项example对象
            SpecificationOptionExample example = new SpecificationOptionExample();
            //创建criteria对象
            SpecificationOptionExample.Criteria criteria = example.createCriteria();
            //设置查询参数，根据外键查询
            criteria.andSpecIdEqualTo(id);
            //根据id查询规格选项
            List<SpecificationOption> optionList = specificationOptionMapper.selectByExample(example);
            //把规格选项集合放入map
            //[{"id":27,"text":"网络","options":[]},{"id":32,"text":"机身内存","options":[]}]
            map.put("options", optionList);
        }
        return specMap;
    }

    //载入所有模板数据缓存  品牌数据、规格数据
    public void refreshRedis() {
        //查询所有模板数据
        List<TbTypeTemplate> typeTemplates = typeTemplateMapper.selectAll();
        //循环所有模板数据
        for (TbTypeTemplate typeTemplate : typeTemplates) {
            //加载模板数据对应的品牌集合
            //[{"id":1,"text":"联想"},{"id":8,"text":"魅族"},{"id":6,"text":"360"},{"id":10,"text":"VIVO"},{"id":13,"text":"长虹"}]
            List<Map> brandIds = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            //存入redis  key = typeTteplateId
            redisUtils.hset("BrandList", String.valueOf(typeTemplate.getId()), brandIds);
            //加载模板数据对应的规格集合
            List<Map> specList = this.getSpecificationOptionById(typeTemplate.getId());
            //存入redis  key = typeTteplateId
            redisUtils.hset("SpecList", String.valueOf(typeTemplate.getId()), specList);
        }
    }

    //[
    //    {"id":1,"text":"内存大小", "options":[{"id":123,"optionName":"4G"},{"id":123,"optionName":"128G"}]},
    //    {"id":2,"text":"网络制式","options":[{"id":123,"optionName":"移动4G"}]},
    //    {"id":3,"text":"屏幕尺寸","options":[{"id":123,"optionName":"16寸"},{"id":124,"optionName":"60寸"}]}
    //]
    /****
     * 根据模板ID查询数据，组装规格集合数据
     * @param typeTemplateId：模板ID
     * @return
     */
    @Override
    public List<Map> getSpecificationOptionById(Long typeTemplateId) {
        //a.前台传入typeTemplateId到Controller
        //b.调用Service
        //        根据typeTemplateId查询模板数据
        TbTypeTemplate template = typeTemplateMapper.selectByPrimaryKey(typeTemplateId);
        //取出spec_ids(当前商品分类拥有的规格数据)  List<Map> = [{"id":34,"text":"网络"},{"id":26,"text":"尺码"}]
        String specIds = template.getSpecIds();
        //将specIds转成List<Map>
        List<Map> specMap = JSON.parseArray(specIds, Map.class);
        //根据spec_ids中的规格ID(id节点)去查询对应的规格选项
        for (Map map : specMap) {
            //获取ID
            Long specId = Long.parseLong(map.get("id").toString());
            //根据ID查询对应的规格选项   select * from tb_specification_option where spec_id=?
            SpecificationOption specificationOption = new SpecificationOption();
            specificationOption.setSpecId(specId);
            List<SpecificationOption> options = specificationOptionMapper.select(specificationOption);
            //c.组装数据结构
            //List<Map<String,Object>>  JSON
            map.put("options", options);
        }
        return specMap;
    }

    @Override
    public PageResult getAllTemplate(Integer page, Integer rows, TbTypeTemplate tbTypeTemplate) {
    //分页实现
        PageHelper.startPage(page,rows);
        //载入所有模板数据缓存  品牌数据、规格数据
        refreshRedis();
        List<TbTypeTemplate> list = typeTemplateMapper.selectAll();
        PageInfo<TbTypeTemplate> info = new PageInfo<>(list);
        return new PageResult(info.getTotal(),
                info.getList(),rows);
    }

    @Override
    public List<TbAttribute> getAllAttrNotPage() {
        return attrMapper.selectAll();
    }
}
