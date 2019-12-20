package com.supergo.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supergo.common.pojo.*;
import com.supergo.common.pojo.es.ESSpec;
import com.supergo.search.cache.GoodsDataEsCache;
import com.supergo.search.mapper.*;
import com.supergo.search.service.ItemSearchService;
import com.supergo.search.utils.Constants;
import com.supergo.search.utils.RedisUtils;
import com.supergo.user.utils.JsonUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.regex.Pattern;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 功能描述:  搜索服务实现类
 *
 * @param:
 * @return:
 * @auther: jackhu
 * @date: 7/29/2019 10:05 AM
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {


    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 功能描述:
     *
     * @param: [typeId]
     * @return: java.lang.String
     * @auther: jackhu
     * @date: 7/29/2019 10:04 AM
     */
    @Override
    public String getCommonData(Integer typeId) {
        String returnStr = "";
        switch (typeId) {
            case Constants.COMMON_TYPE.TEMPLATE: {
                List<?> list = typeTemplateMapper.selectAll();
                if (list != null) {
                    returnStr = JSONObject.toJSONString(list);
                }
                break;
            }
            case Constants.COMMON_TYPE.BRAND: {
                List<?> list = brandMapper.selectAll();
                if (list != null) {
                    returnStr = JSONObject.toJSONString(list);
                }
                break;
            }
            default:
                break;
        }
        return returnStr;
    }

    /**
     * 功能描述:
     *
     * @param: [index, suggestStr]
     * @return: java.util.List<java.lang.String>
     * @auther: jackhu
     * @date: 7/29/2019 10:04 AM
     */
    @Override
    public List<String> suggest(String index, String suggestStr) {
        // 查询条件生成对象
        CompletionSuggestionBuilder suggestionBuilder = new CompletionSuggestionBuilder("suggest");
        // 设置分词查询类型
        suggestionBuilder.analyzer("ik_max_word");
        suggestionBuilder.text(suggestStr);
        // 查询值
        SearchResponse response = elasticsearchTemplate.getClient().prepareSearch(index).setTypes("goods")
                .setQuery(QueryBuilders.matchAllQuery())
                .suggest(new SuggestBuilder().addSuggestion("my-suggest-1", suggestionBuilder)).get();
        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestion = suggest.getSuggestion("my-suggest-1");
        List<CompletionSuggestion.Entry> list = suggestion.getEntries();
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<CompletionSuggestion.Entry.Option> options = list.get(i).getOptions();
            for (int j = 0; j < options.size(); j++) {
                if (options.get(j) instanceof CompletionSuggestion.Entry.Option) {
                    CompletionSuggestion.Entry.Option op = options.get(j);
                    returnList.add(op.getText().toString());
                    System.out.println(op.getScore() + "--" + op.getText());
                }
            }
        }
        return returnList;
    }

    /**
     * 功能描述:  搜索联想词
     *
     * @param: [inputText]
     * @return: java.util.Set<java.lang.String>
     * @auther: jackhu
     * @date: 7/29/2019 10:04 AM
     */
    @Override
    public Set<String> searchAssociation(String inputText) {
        String index = "goods";
        String type = "goods";
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        String field;
        // 对我们传入的搜索内容进行判断，是中文，还是拼音
        if (checkLetter(inputText)) {
            field = "goodsName.keyword_pinyin";
        } else if (checkChinese(inputText)) {
            field = "goodsName";
        } else {
            field = "goodsName.keyword_pinyin";
        }
        Set<String> results = this.getSuggestWord(index, type, field, inputText, queryBuilder);
        //结果为空且是拼音，可以尝试拼音首字母提示
        if (results.size() == 0 && checkLetter(inputText)) {
            field = "goodsName.keyword_first_py";
            results = this.getSuggestWord(index, type, field, inputText, queryBuilder);
        }
        for (String result : results) {
            System.out.println(result);
        }
        return results;
    }

    /**
     * 功能描述:  进行搜索
     *
     * @param: [searchMap]
     * @return: java.util.Map
     * @auther: jackhu
     * @date: 7/29/2019 10:02 AM
     */
    @Override
    public Map searchAll(Map searchMap) {
        Map<String, Object> finallyMap = new HashMap<>(16);
        Map maps = new HashMap(10);
        Long docCount = 0L;
        // 判断是否有三级分类,若存在跳过聚合搜索
        String category3Id = null;
        String classify = (String) searchMap.get("classify");
        if (!ObjectUtils.isEmpty(classify)) {
            // 跳过聚合直接搜索索引
            category3Id = classify;
        } else {
            SearchRequestBuilder srb = elasticsearchTemplate.getClient().prepareSearch("goods");
            // 关键字
            String key = (String) searchMap.get("keywords");
            if (ObjectUtils.isEmpty(key)) {
                // 若关键字为空，则直接返回
                return new HashMap();
            }
            QueryBuilder qb = QueryBuilders.multiMatchQuery(key, "goodsName", "itemName");
            srb.setQuery(qb);
            // 聚合搜索
            TermsAggregationBuilder classifyBuilder = AggregationBuilders.terms("classify_count").field("classify");
            srb.addAggregation(classifyBuilder);
            SearchResponse sr = srb.get();
            Terms terms1 = sr.getAggregations().get("classify_count");
            List<? extends Terms.Bucket> buckets = terms1.getBuckets();
            if (ObjectUtils.isEmpty(buckets)) {
                // goods 索引命中失败
                System.out.println("命中Goods索引失败!直接返回...");
                return new HashMap();
            }
            for (int i = 0; i < buckets.size(); i++) {
                Long key1 = (Long) buckets.get(i).getKey();
                if (ObjectUtils.isEmpty(key1)) {
                    continue;
                }
                SearchRequestBuilder goodsSrb = elasticsearchTemplate.getClient().prepareSearch("classify_" + key1);
                try {
                    goodsSrb.get();
                } catch (Exception e) {
                    continue;
                }
                category3Id = String.valueOf(key1);
                break;
            }
        }
        if (ObjectUtils.isEmpty(category3Id)){
            return new HashMap();
        }
        // 根据分类查询对应索引
        SearchRequestBuilder goodsSrb = elasticsearchTemplate.getClient().prepareSearch("classify_" + category3Id);
        // 组装查询条件
        Long templateId = this.assembleConditions(category3Id, goodsSrb, searchMap);
        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span><font color='red'>");
        highlightBuilder.postTags("</font></span>");
        highlightBuilder.field("goodsName");
        goodsSrb.highlighter(highlightBuilder);
        TermsAggregationBuilder productCountBuilder = AggregationBuilders.terms("goods_count").field("goodsId");
        goodsSrb.addAggregation(productCountBuilder);
        // 执行查询，获取结果
        SearchResponse sr1 = goodsSrb.get();
        List<Map<String, Object>> valueList = this.responseToList(sr1);
        System.out.println("valueList:" + valueList);
        for (Map map : valueList) {
            System.out.println("item:" + map);
        }
        Terms goodsTerms = sr1.getAggregations().get("goods_count");
        List<? extends Terms.Bucket> buckets = goodsTerms.getBuckets();
        if (!ObjectUtils.isEmpty(buckets)) {
            docCount = buckets.get(0).getDocCount();
        }
        maps.put("count", docCount);
        maps.put("valueList", valueList);
        finallyMap.put("listData", maps);
        // 获取搜索条件并缓存
        Map<String, Object> conditionData = this.getConditionMap(templateId, Long.valueOf(category3Id));
        finallyMap.put("conditionData", conditionData);
        return finallyMap;
    }

    /**
     * 功能描述:  组装查询条件
     *
     * @param: [category3Id, goodsSrb, searchMap]
     * @return: java.lang.Long
     * @auther: jackhu
     * @date: 7/29/2019 10:02 AM
     */
    private Long assembleConditions(String category3Id, SearchRequestBuilder goodsSrb, Map searchMap) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 通过分类id查询模板
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andCategory3IdEqualTo(Long.valueOf(category3Id));
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        if (ObjectUtils.isEmpty(goodsList)) {
            return null;
        }
        Goods goods = goodsList.get(0);
        Long typeTemplateId = goods.getTypeTemplateId();
        List<ESSpec> esSpecs = (List<ESSpec>) GoodsDataEsCache.cacheData.get(typeTemplateId).get(1);
        // 规格查询
        for (ESSpec esSpec : esSpecs
        ) {
            String key = esSpec.getSpecShortName();
            String value = (String) searchMap.get(key);
            if (!ObjectUtils.isEmpty(value)) {
                boolQueryBuilder.must(QueryBuilders.matchQuery(key, value));
                goodsSrb.setQuery(boolQueryBuilder);
            }
        }
        List<TbAttribute> esAttrs = (List<TbAttribute>) GoodsDataEsCache.cacheData.get(typeTemplateId).get(2);
        // 扩展属性查询
        for (TbAttribute esAttr : esAttrs
        ) {
            String key = esAttr.getAttributeKey();
            String value = (String) searchMap.get(key);
            if (!ObjectUtils.isEmpty(value)) {
                boolQueryBuilder.must(QueryBuilders.matchQuery(key, value));
                goodsSrb.setQuery(boolQueryBuilder);
            }
        }
        // 关键字
        String keyword = (String) searchMap.get("keywords");
        if (!ObjectUtils.isEmpty(keyword)) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(keyword, "goodsName", "title"));
            goodsSrb.setQuery(boolQueryBuilder);
        }
        // 品牌
        Integer brandId = (Integer) searchMap.get("brand");
        if (!ObjectUtils.isEmpty(brandId)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("brandId", brandId));
            goodsSrb.setQuery(boolQueryBuilder);
        }
        // 价格
        String priceStr = (String) searchMap.get("price");
        if (!ObjectUtils.isEmpty(priceStr)) {
            String[] priceScope = null;
            try {
                priceScope = priceStr.split(":");
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(priceScope[0]).lte(priceScope[1]));
            goodsSrb.setQuery(boolQueryBuilder);
        }
        return typeTemplateId;
    }

    /**
     * 功能描述:  获取搜索条件并缓存到 Redis
     *
     * @param: [templateId, needId]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @auther: jackhu
     * @date: 7/29/2019 9:58 AM
     */
    private Map<String, Object> getConditionMap(Long templateId, Long needId) {
        List<String> prices = null;
        Map<String, Object> conditionsMap = new HashMap<>(50);
        List<Brand> brands = (List<Brand>) GoodsDataEsCache.cacheData.get(templateId).get(0);
        // 获取规格
        List<ESSpec> esSpecs = (List<ESSpec>) GoodsDataEsCache.cacheData.get(templateId).get(1);
        // 获取扩展属性
        List<TbAttribute> esAttrs = (List<TbAttribute>) GoodsDataEsCache.cacheData.get(templateId).get(2);
        this.handleAttrData(esAttrs);
        // 获取价格区间
        String priceStr = goodsMapper.getPricePeriod(needId);
        if (!ObjectUtils.isEmpty(priceStr)) {
            String[] priceArr = null;
            try {
                priceArr = priceStr.split(",");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!ObjectUtils.isEmpty(priceArr) && priceArr.length == 2) {
                // 存储价格区间
                prices = this.getPricePeriods(priceArr);
            }
        }
        this.cacheToRedis(needId, brands, esSpecs, esAttrs, prices);
        conditionsMap.put("attrs", esAttrs);
        conditionsMap.put("specs", esSpecs);
        conditionsMap.put("brands", brands);
        conditionsMap.put("price", prices);
        return conditionsMap;
    }

    private void handleAttrData(List<TbAttribute> esAttrs) {
        for (TbAttribute tbAttribute : esAttrs) {
            List<Map<String, String>> attrs = new ArrayList<>();
            String attributeOptions = tbAttribute.getAttributeOptions();
            JSONArray objects = JSONArray.parseArray(attributeOptions);
            for (int i = 0; i < objects.size(); i++) {
                Map<String, String> map = new HashMap<>();
                JSONObject jsonObject = objects.getJSONObject(i);
                String optionName = (String) jsonObject.get("optionName");
                String sort = (String) jsonObject.get("sort");
                map.put("optionName", optionName);
                map.put("sort", sort);
                attrs.add(map);
            }
            tbAttribute.setAttrMap(attrs);
        }
    }

    /**
     * 功能描述:  缓存到 Redis
     *
     * @param: [needId, brands, esSpecs, esAttrs]
     * @return: void
     * @auther: jackhu
     * @date: 7/29/2019 10:00 AM
     */
    private void cacheToRedis(Long needId, List<Brand> brands, List<ESSpec> esSpecs, List<TbAttribute> esAttrs, List<String> prices) {
        String attrs_json = (String) redisUtils.hget("conditionData_" + needId, "attrs");
        String specs_json = (String) redisUtils.hget("conditionData_" + needId, "specs");
        String brands_json = (String) redisUtils.hget("conditionData_" + needId, "brands");
        String price_json = (String) redisUtils.hget("conditionData_" + needId, "prices");
        if (ObjectUtils.isEmpty(attrs_json)) {
            redisUtils.hset("conditionData_" + needId, "attrs", JSON.toJSONString(esAttrs));
        }
        if (ObjectUtils.isEmpty(specs_json)) {
            redisUtils.hset("conditionData_" + needId, "specs", JSON.toJSONString(esSpecs));
        }
        if (ObjectUtils.isEmpty(brands_json)) {
            redisUtils.hset("conditionData_" + needId, "brands", JsonUtils.objectToJson(brands));
        }
        if (ObjectUtils.isEmpty(price_json)) {
            redisUtils.hset("conditionData_" + needId, "prices", JsonUtils.objectToJson(prices));
        }
    }

    /**
     * 功能描述:  获取价格区间
     *
     * @param: [priceArr, conditionsMap]
     * @return: void
     * @auther: jackhu
     * @date: 7/29/2019 10:00 AM
     */
    private List<String> getPricePeriods(String[] priceArr) {
        List<String> result = new ArrayList<>();
        int showPriceNum = 5;
        int minPrice = new Double(priceArr[0]).intValue();
        int maxPrice = new Double(priceArr[1]).intValue();
        int perPrice = new Double(Math.ceil((maxPrice - minPrice) / showPriceNum)).intValue();
        if (perPrice > 0) {
            result.add("0-" + perPrice);
            int stepPrice = perPrice;
            for (int addPrice = stepPrice + 1; addPrice < maxPrice; ) {
                if (result.size() == showPriceNum) {
                    break;
                }
                //下个区间结束值
                stepPrice = addPrice + perPrice;
                //除去首个数字外,剩下所有为 9
                String stepPriceStr = String.valueOf(stepPrice);
                stepPrice = Integer.parseInt(this.setMaxLimit(stepPriceStr));
                result.add(addPrice + "-" + stepPrice);
                addPrice = stepPrice + 1;
            }
            //置换max价格,把数据最后的值替换为最大值
            String str = result.get(result.size() - 1);
            result.set(result.size() - 1, str.replace(str.substring(str.indexOf("-") + 1), maxPrice + ""));
        }
        return result;
    }

    /**
     * 功能描述:
     *
     * @param: [stepPriceStr]
     * @return: java.lang.String
     * @auther: jackhu
     * @date: 7/29/2019 1:54 PM
     */
    private String setMaxLimit(String stepPriceStr) {
        char[] chars = stepPriceStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != 0) {
                chars[i] = '9';
            }
        }
        return new String(chars);
    }

    /**
     * 功能描述:  删除索引
     *
     * @param: [ids]
     * @return: void
     * @auther: jackhu
     * @date: 7/29/2019 10:02 AM
     */
    @Override
    public void deleteByGoodsIds(Long[] ids) {
        // itemDao.deleteByGoodsIdIn(Arrays.asList(ids));
    }


    /**
     * 功能描述:
     *
     * @param: [index, type, field, text, queryBuilder]
     * @return: java.util.Set<java.lang.String>
     * @auther: jackhu
     * @date: 7/29/2019 10:02 AM
     */
    private Set<String> getSuggestWord(String index, String type, String field, String text, QueryBuilder queryBuilder) {
        Set<String> results = new TreeSet<String>();
        CompletionSuggestionBuilder suggestionBuilder = new CompletionSuggestionBuilder(field);
        suggestionBuilder.text(text);
        suggestionBuilder.size(20);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("my-suggest-1", suggestionBuilder);
        SearchRequestBuilder searchRequestBuilder = elasticsearchTemplate.getClient().prepareSearch(index).setTypes(type);
        searchRequestBuilder.setExplain(false);
        searchRequestBuilder.setSize(0);
        searchRequestBuilder.setQuery(queryBuilder);
        searchRequestBuilder.suggest(suggestBuilder);
        searchRequestBuilder.setFetchSource(false);
        SearchResponse resp = searchRequestBuilder.execute().actionGet();
        Suggest sugg = resp.getSuggest();
        CompletionSuggestion suggestion = sugg.getSuggestion("my-suggest-1");
        List<CompletionSuggestion.Entry> list = suggestion.getEntries();
        for (int i = 0; i < list.size(); i++) {
            List<? extends Suggest.Suggestion.Entry.Option> options = list.get(i).getOptions();
            for (Suggest.Suggestion.Entry.Option op : options) {
                results.add(op.getText().toString());
            }
        }
        return results;
    }

    /**
     * 功能描述:  验证字母
     *
     * @param: [cardNum]
     * @return: boolean
     * @auther: jackhu
     * @date: 7/29/2019 10:00 AM
     */
    public static boolean checkLetter(String cardNum) {
        String regex = "^[A-Za-z]+$";
        return Pattern.matches(regex, cardNum);
    }

    /**
     * 功能描述:  验证中文
     *
     * @param: [chinese]
     * @return: boolean
     * @auther: jackhu
     * @date: 7/29/2019 10:01 AM
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }

    /**
     * 功能描述:  将 Response 转换成List
     *
     * @param: [response]
     * @return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @auther: jackhu
     * @date: 7/29/2019 10:01 AM
     */
    private List<Map<String, Object>> responseToList(SearchResponse response) {
        SearchHits hits = response.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < hits.getHits().length; i++) {
            SearchHit hit = hits.getAt(i);
            Map<String, Object> map = hit.getSourceAsMap();
            //插入高亮信息
            Map<String, HighlightField> highLight = hit.getHighlightFields();
            for (String key : highLight.keySet()) {
                map.put(key, highLight.get(key).getFragments()[0].toString());
            }
            list.add(map);
        }
        return list;
    }
}
