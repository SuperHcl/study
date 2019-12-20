package com.supergo.search.cache;

import com.netflix.discovery.converters.Auto;
import com.supergo.common.pojo.Brand;
import com.supergo.common.pojo.Goods;
import com.supergo.common.pojo.TbAttribute;
import com.supergo.common.pojo.es.ESSpec;
import com.supergo.search.mapper.GoodsMapper;
import com.supergo.search.utils.RedisUtils;
import com.supergo.search.utils.SpecAndAttrData;
import com.supergo.user.utils.JsonUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GoodsDataEsCache
 * @Description TODO
 * @Author wesker
 * @Date 7/16/2019 6:55 PM
 * @Version 1.0
 **/
@Component
public class GoodsDataEsCache implements CommandLineRunner {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private SpecAndAttrData specAndAttrData;

    @Autowired
    private RedisUtils redisUtils;

    public static final Map<String, XContentBuilder> cacheMap = new HashMap<>(100);
    public static final Map<Long, List<Object>> cacheData = new HashMap<>(100);

    @Override
    public void run(String... args) throws Exception {
        List<Goods> goodsList = goodsMapper.selectAll();
        for (Goods goods : goodsList
        ) {
            Long typeTemplateId = goods.getTypeTemplateId();
            if (!ObjectUtils.isEmpty(typeTemplateId)) {
                List<TbAttribute> attrData;
                List<ESSpec> specData;
                List<Brand> brandData;
                List<Object> objs = new ArrayList<>(3);
                String attrJson = (String) redisUtils.get("data_attr_" + typeTemplateId);
                if (!ObjectUtils.isEmpty(attrJson)){
                    attrData = JsonUtils.jsonToList(attrJson, TbAttribute.class);
                } else {
                    attrData = specAndAttrData.getAttrData(typeTemplateId);
                    redisUtils.set("data_attr_" + typeTemplateId, JsonUtils.objectToJson(attrData));
                }
                String specJson = (String) redisUtils.get("data_spec_" + typeTemplateId);
                if (!ObjectUtils.isEmpty(specJson)){
                    specData = JsonUtils.jsonToList(specJson, ESSpec.class);
                } else {
                    specData = specAndAttrData.getSpecData(typeTemplateId);
                    redisUtils.set("data_spec_" + typeTemplateId, JsonUtils.objectToJson(specData));
                }
                String brandJson = (String) redisUtils.get("data_brand_" + typeTemplateId);
                if (!ObjectUtils.isEmpty(brandJson)){
                    brandData = JsonUtils.jsonToList(brandJson, Brand.class);
                } else {
                    brandData = specAndAttrData.getBrandData(typeTemplateId);
                    redisUtils.set("data_brand_" + typeTemplateId, JsonUtils.objectToJson(brandData));
                }
                objs.add(brandData);
                objs.add(specData);
                objs.add(attrData);
                cacheData.put(typeTemplateId, objs);
            }
        }
        System.out.println("初始化缓存完成...");
    }
}
