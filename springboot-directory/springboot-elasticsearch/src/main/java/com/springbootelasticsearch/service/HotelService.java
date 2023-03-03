package com.springbootelasticsearch.service;

import cn.hutool.core.bean.BeanUtil;
import com.springbootelasticsearch.entity.HotelEsModel;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/27 14:29
 */
@Service
public class HotelService extends AbstractBaseEsService {

    /**
     * keyword match查询，match是全文查询，在查询时，es会先分析查询字符串没然后根据分词构建查询。
     *
     * @param titleKeyword title
     * @return result
     */
    public List<HotelEsModel> getHotelFromTitle(String titleKeyword) {
        List<HotelEsModel> result = new ArrayList<>();

        SearchRequest searchRequest = new SearchRequest("hotel");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", titleKeyword));
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if (RestStatus.OK == searchResponse.status()) {
                for (SearchHit hit : searchResponse.getHits()) {
                    HotelEsModel hotelEsModel = new HotelEsModel();
                    hotelEsModel.setId(hit.getId());
                    hotelEsModel.setScore(hit.getScore());
                    hotelEsModel.setIndex(hit.getIndex());

                    // es返回的数据，可以从kibana查询中看出来数据结构都有哪些。
                    // source里面是我们真正要关注的响应数据
                    Map<String, Object> sourceMap = hit.getSourceAsMap();
                    hotelEsModel.setTitle((String) sourceMap.get("title"));
                    hotelEsModel.setCity((String) sourceMap.get("city"));
                    hotelEsModel.setPrice((Double) sourceMap.get("price"));

                    result.add(hotelEsModel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void singleSave(HotelEsModel hotelEsModel)  {
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(hotelEsModel, true, true);
        logger.info(stringObjectMap.toString());
        singleWriteDoc(stringObjectMap, "hotel", "");
    }

    public void bulkSave(String titleKey, String cityKey, double price) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            HotelEsModel esModel = new HotelEsModel();
            esModel.setTitle(titleKey + i);
            esModel.setCity(cityKey + i);
            esModel.setPrice(price + i);
            esModel.setCreateTime(new Date());
            Map<String, Object> stringObjectMap = BeanUtil.beanToMap(esModel, true, true);
            data.add(stringObjectMap);
        }

        logger.info(data.toString());
        bulkWriteDocs(data, "hotel");
    }

}
