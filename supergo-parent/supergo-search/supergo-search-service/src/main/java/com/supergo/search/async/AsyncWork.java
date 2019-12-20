package com.supergo.search.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supergo.common.pojo.es.GoodsEs;
import com.supergo.search.cache.GoodsDataEsCache;
import com.supergo.search.mapper.ItemMapper;
import com.supergo.search.utils.ESIndexData;
import com.supergo.user.utils.CollectionsUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @ClassName AsyncWork
 * @Description TODO
 * @Author wesker
 * @Date 7/17/2019 1:56 PM
 * @Version 1.0
 **/
@Component
public class AsyncWork {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ESIndexData esIndexData;

    @Autowired
    private ItemMapper itemMapper;

    @Value(value = "classpath:indexs/settings.json")
    private Resource settingsResource;

    @Value(value = "classpath:indexs/mapping.json")
    private Resource mappingsResource;

    @Async
    public void importDataToES() {
        Long beginTime = System.currentTimeMillis();
        System.out.println("开始异步任务...");
        try {
            // 建立主索引及mapping
            this.doCreateMainIndex();
            // 创建三级分类索引索引
            this.doCreateIndex();
            // 创建三级分类mapping
            this.doCreateMapping();
            // 导入主索引数据
            this.importMainData();
            // 导入三级分类索引数据
            this.importData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("结束异步任务... 耗时:" + ((endTime - beginTime) / 1000) + " s");
    }

    private void importMainData() throws Exception {
        Client client = elasticsearchTemplate.getClient();
        BulkRequestBuilder builder = elasticsearchTemplate.getClient().prepareBulk();
        // 获取所有产品名称
        List<GoodsEs> itemEsList = itemMapper.selectMainIndexData();
        // 添加索引
        for (GoodsEs es : itemEsList) {
            XContentBuilder xContentBuilder = jsonBuilder().startObject().field("goodsName", es.getGoodsName()).field("itemName", es.getTitle()).field("classify", es.getClassify()).endObject();
            builder.add(client.prepareIndex("goods", "goods").setSource(xContentBuilder));
        }
        builder.execute().actionGet();
    }

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 7/22/2019 2:35 PM
     */
    private void doCreateMainIndex() {
        try {
            String lineSetting;
            String lineMapping;
            // 读取文件，获取json
            BufferedReader settingsReader = new BufferedReader(new InputStreamReader(settingsResource.getInputStream()));
            BufferedReader mappingsReader = new BufferedReader(new InputStreamReader(mappingsResource.getInputStream()));
            StringBuilder settings = new StringBuilder();
            StringBuilder mappings = new StringBuilder();
            while ((lineSetting = settingsReader.readLine()) != null) {
                settings.append(lineSetting);
            }
            while ((lineMapping = mappingsReader.readLine()) != null) {
                mappings.append(lineMapping);
            }
            String settingsStr = settings.toString();
            String mappingsStr = mappings.toString();
            elasticsearchTemplate.getClient().admin().indices().prepareCreate("goods").setSettings(settingsStr, XContentType.JSON).execute().actionGet();
            PutMappingRequest mapping = Requests.putMappingRequest("goods").type("goods").source(mappingsStr, XContentType.JSON);
            elasticsearchTemplate.getClient().admin().indices().putMapping(mapping).actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @auther: wesker
     * @date: 7/22/2019 3:53 PM
     */
    private void importData() {
        BulkRequestBuilder builder = elasticsearchTemplate.getClient().prepareBulk();
        // 查询数据
        List<GoodsEs> itemEss = itemMapper.queryDbToIndex();
        // 整理数据
        List<List<GoodsEs>> groupList = CollectionsUtils.groupList(itemEss, Comparator.comparing(t -> t.getClassify()));
        // 将扩展属性转成map
        this.handleAttrAndSpec(groupList);
        for (List<GoodsEs> itemEs : groupList) {
            for (GoodsEs es : itemEs) {
                String json = this.handleObjToJson(es);
                builder.add(elasticsearchTemplate.getClient().prepareIndex("classify_" + es.getClassify(), "goods").setSource(json, XContentType.JSON));
            }
            builder.execute().actionGet();
        }
    }

    private String handleObjToJson(GoodsEs es) {
        JSONObject object = new JSONObject();
        Map attrMap = es.getAttrMap();
        Map specMap = es.getSpecMap();
        String json = es.toString();
        JSONObject source = JSONObject.parseObject(json);
        object.putAll(source);
        if (!ObjectUtils.isEmpty(attrMap)) {
            JSONObject attrJson = JSONObject.parseObject(JSON.toJSONString(attrMap));
            object.putAll(attrJson);
        }
        if (!ObjectUtils.isEmpty(specMap)) {
            JSONObject specJson = JSONObject.parseObject(JSON.toJSONString(specMap));
            object.putAll(specJson);
        }
        return JSON.toJSONString(object);
    }

    private void handleAttrAndSpec(List<List<GoodsEs>> groupList) {
        for (int i = 0; i < groupList.size(); i++) {
            List<GoodsEs> itemEs = groupList.get(i);
            for (int j = 0; j < itemEs.size(); j++) {
                GoodsEs es = itemEs.get(j);
                String attr = es.getAttr();
                String spec = es.getSpec();
                if (!ObjectUtils.isEmpty(attr)) {
                    es.setAttrMap(this.handleAttrToMap(attr));
                }
                if (!ObjectUtils.isEmpty(spec)) {
                    es.setSpecMap(this.handleSpecToMap(spec));
                }
            }
        }
    }

    private Map handleSpecToMap(String spec) {
        Map specMap = new HashMap();
        JSONArray arrays = JSONArray.parseArray(spec);
        for (int i = 0; i < arrays.size(); i++) {
            StringBuilder builder = new StringBuilder();
            JSONObject jsonObject = arrays.getJSONObject(i);
            String attributeName = (String) jsonObject.get("attributeName");
            String attributeValue = jsonObject.getString("attributeValue");
            JSONArray objects = JSONArray.parseArray(attributeValue);
            for (int j = 0; j < objects.size(); j++) {
                if (j == objects.size() - 1) {
                    builder.append(objects.getString(j));
                } else {
                    builder.append(objects.getString(j)).append(",");
                }
            }
            specMap.put(attributeName, builder.toString());
        }
        return specMap;
    }

    private Map handleAttrToMap(String attr) {
        Map attrMap = new HashMap();
        JSONArray arrays = JSONArray.parseArray(attr);
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject jsonObject = arrays.getJSONObject(i);
            String attributeKey = (String) jsonObject.get("attributeKey");
            String optionName = (String) jsonObject.get("optionName");
            attrMap.put(attributeKey, optionName);
        }
        return attrMap;
    }


    private void doCreateIndex() {
        BulkRequestBuilder bulkRequest = elasticsearchTemplate.getClient().prepareBulk();
        try {
            if (ObjectUtils.isEmpty(GoodsDataEsCache.cacheMap)) {
                esIndexData.getEsIndexData();
            }
            Iterator<String> keys = GoodsDataEsCache.cacheMap.keySet().iterator();
            while (keys.hasNext()) {
                String cName = keys.next();
                XContentBuilder builder = jsonBuilder().startObject().field("_index", cName).field("_type", "goods").endObject();
                bulkRequest.add(elasticsearchTemplate.getClient().prepareIndex(cName, "goods").setSource(builder));
            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCreateMapping() throws Exception {
        if (ObjectUtils.isEmpty(GoodsDataEsCache.cacheMap)) {
            throw new Exception("缓存无数据...");
        }
        GoodsDataEsCache.cacheMap.forEach((cName, builder) -> {
            System.out.println(Strings.toString(builder));
            elasticsearchTemplate.getClient().admin().indices().preparePutMapping(cName).setType("goods").setSource(builder).get();
        });
    }
}
