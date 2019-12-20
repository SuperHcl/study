package com.supergo.search.utils;

import com.supergo.common.pojo.Goods;
import com.supergo.common.pojo.TbAttribute;
import com.supergo.common.pojo.es.ESSpec;
import com.supergo.search.cache.GoodsDataEsCache;
import com.supergo.search.mapper.GoodsMapper;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @ClassName ESIndexData
 * @Description TODO
 * @Author wesker
 * @Date 7/16/2019 6:57 PM
 * @Version 1.0
 **/
@Component
public class ESIndexData {

    @Autowired
    private GoodsMapper goodsMapper;

    public void getEsIndexData() {
        List<String> fieldNames = new ArrayList<>();
        List<Goods> goods = goodsMapper.selectAll();
        for (Goods good : goods) {
            Long category3Id = good.getCategory3Id();
            if (ObjectUtils.isEmpty(category3Id)) {
                continue;
            }
            String categoryName = "classify_" + category3Id;
            Long typeTemplateId = good.getTypeTemplateId();
            List<TbAttribute> attrData = (List<TbAttribute>) GoodsDataEsCache.cacheData.get(typeTemplateId).get(2);
            List<ESSpec> specData = (List<ESSpec>) GoodsDataEsCache.cacheData.get(typeTemplateId).get(1);
            for (TbAttribute tbAttribute : attrData
            ) {
                fieldNames.add(tbAttribute.getAttributeKey());
            }
            for (ESSpec esSpec : specData
            ) {
                fieldNames.add(esSpec.getSpecShortName());
            }
            this.generatorXContentBuilder(categoryName, fieldNames);
            fieldNames.clear();
        }
    }

    private void generatorXContentBuilder(String categoryName, List<String> fieldNames) {
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("title")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("cn").field("type", "text").field("analyzer", "ik_smart").endObject()
                    .startObject("en").field("type", "text").field("analyzer", "english").endObject()
                    .endObject()
                    .endObject()
                    .startObject("goodsName")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("cn").field("type", "text").field("analyzer", "ik_smart").endObject()
                    .startObject("en").field("type", "text").field("analyzer", "english").endObject()
                    .endObject()
                    .endObject()
                    .startObject("imagePath")
                    .field("type", "text")
                    .endObject()
                    .startObject("sellerName")
                    .field("type", "text")
                    .endObject()
                    .startObject("goodsId")
                    .field("type", "long")
                    .endObject()
                    .startObject("brandId")
                    .field("type", "long")
                    .endObject()
                    .startObject("brandName")
                    .field("type", "text")
                    .endObject()
                    .startObject("classify")
                    .field("type", "long")
                    .endObject()
                    .startObject("classifyName")
                    .field("type", "text")
                    .endObject()
                    .startObject("price")
                    .field("type", "double")
                    .endObject();
            for (String fieldName : fieldNames) {
                builder.startObject(fieldName).field("type", "text").endObject();
            }
            builder.endObject().endObject();
            GoodsDataEsCache.cacheMap.put(categoryName, builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
