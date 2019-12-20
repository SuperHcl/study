package com.supergo.search.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supergo.common.pojo.*;
import com.supergo.common.pojo.es.ESSpec;
import com.supergo.search.mapper.BrandMapper;
import com.supergo.search.mapper.SpecificationMapper;
import com.supergo.search.mapper.SpecificationOptionMapper;
import com.supergo.search.mapper.TypeTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecAndAttrData {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Autowired
    private BrandMapper brandMapper;

    public List<Brand> getBrandData(Long typeTemplateId) {
        List<Brand> brands = new ArrayList<>();
        TypeTemplate typeTemplate;
        if (!ObjectUtils.isEmpty(typeTemplateId)) {
            try {
                typeTemplate = typeTemplateMapper.selectByPrimaryKey(typeTemplateId);
                if (ObjectUtils.isEmpty(typeTemplate)) {
                    return brands;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return brands;
            }
            String brandStr = typeTemplate.getBrandIds();
            JSONArray objects = this.getJsonArray(brandStr);
            for (int i = 0; i < objects.size(); i++) {
                JSONObject jsonObject = objects.getJSONObject(i);
                Integer brandId = (Integer) jsonObject.get("id");
                Brand brand = brandMapper.selectByPrimaryKey(brandId);
                brands.add(brand);
            }
        }
        return brands;
    }

    private JSONArray getJsonArray(String str) {
        return JSONArray.parseArray(str);
    }

    public List<ESSpec> getSpecData(Long typeTemplateId) {
        List<ESSpec> esSpecs = new ArrayList<>();
        TypeTemplate typeTemplate;
        SpecificationOptionExample specificationOptionExample = new SpecificationOptionExample();
        if (!ObjectUtils.isEmpty(typeTemplateId)) {
            try {
                typeTemplate = typeTemplateMapper.selectByPrimaryKey(typeTemplateId);
                if (ObjectUtils.isEmpty(typeTemplate)) {
                    return esSpecs;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return esSpecs;
            }
            String specStr = typeTemplate.getSpecIds();
            JSONArray objects = this.getJsonArray(specStr);
            for (int i = 0; i < objects.size(); i++) {
                ESSpec esSpec = new ESSpec();
                JSONObject jsonObject = objects.getJSONObject(i);
                Integer specId = (Integer) jsonObject.get("id");
                String shortName = (String) jsonObject.get("text");
                Specification specification = specificationMapper.selectByPrimaryKey(specId);
                specificationOptionExample.createCriteria().andSpecIdEqualTo(specId.longValue());
                List<SpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(specificationOptionExample);
                esSpec.setSpecification(specification);
                esSpec.setSpecShortName(shortName);
                esSpec.setSpecificationOptionList(specificationOptions);
                specificationOptionExample.clear();
                esSpecs.add(esSpec);
            }
        }
        return esSpecs;
    }


    public List<TbAttribute> getAttrData(Long typeTemplateId) {
        TypeTemplate typeTemplate;
        List<TbAttribute> esAttrs = new ArrayList<>();
        if (!ObjectUtils.isEmpty(typeTemplateId)) {
            // 查询对应的附加属性
            try {
                typeTemplate = typeTemplateMapper.selectByPrimaryKey(typeTemplateId);
                if (ObjectUtils.isEmpty(typeTemplate)) {
                    return esAttrs;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return esAttrs;
            }
            // 取 attr 的 json串
            String customAttributeItems = typeTemplate.getCustomAttributeItems();
            if (!ObjectUtils.isEmpty(customAttributeItems)) {
                JSONArray objects = this.getJsonArray(customAttributeItems);
                for (int i = 0; i < objects.size(); i++) {
                    JSONObject obj = objects.getJSONObject(i);
                    if (!ObjectUtils.isEmpty(obj)) {
                        TbAttribute esAttr = new TbAttribute();
                        String attributeKey = (String) obj.get("attributeKey");
                        Integer attributeId = (Integer) obj.get("attributeId");
                        String attributeName = (String) obj.get("attributeName");
                        String attributeOptions = obj.getString("attributeOptions");
                        esAttr.setAttributeId(attributeId);
                        esAttr.setAttributeKey(attributeKey);
                        esAttr.setAttributeName(attributeName);
                        esAttr.setAttributeOptions(attributeOptions);
                        esAttrs.add(esAttr);
                    }
                }
            }
        }
        return esAttrs;
    }
}
