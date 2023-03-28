package com.springbootelasticsearch.service.impl;

import com.springbootelasticsearch.service.InnService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * inn客栈service
 *
 * @author Hu.ChangLiang
 * @date 2023/3/28 15:58
 */
@Service
public class InnServiceImpl implements InnService {
    @Resource
    private RestHighLevelClient client;

    @Override
    public void matchAllSearch() {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery().boost(2.0f);
        searchSourceBuilder.query(matchAllQueryBuilder);
        searchSourceBuilder.fetchSource(new String[] {"title", "city", "amenities"}, null);
        searchRequest.source(searchSourceBuilder);
        searchAndPrintResult(searchRequest);

    }


    public void searchAndPrintResult(SearchRequest searchRequest) {
        try {
            //执行搜索
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            //获取搜索结果集
            SearchHits searchHits = searchResponse.getHits();
            //遍历搜索结果集
            for (SearchHit searchHit : searchHits) {
                //获取索引名称
                String index = searchHit.getIndex();
                //获取文档_id
                String id = searchHit.getId();
                //获取得分
                Float score = searchHit.getScore();
                //获取文档内容
                String source = searchHit.getSourceAsString();
                System.out.printf("index=[%s], id=[%s], score=[%.2f], source=[%s]%n", index, id, score, source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
