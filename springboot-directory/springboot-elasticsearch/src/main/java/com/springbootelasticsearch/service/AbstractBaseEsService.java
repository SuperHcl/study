package com.springbootelasticsearch.service;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Hu.ChangLiang
 * @date 2023/3/3 14:10
 */

public abstract class AbstractBaseEsService {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractBaseEsService.class);
    @Resource
    protected RestHighLevelClient restHighLevelClient;

    /**
     * 单文档写入
     *
     * @param dataMap   写入数据源
     * @param indexName 索引名称
     * @param indexId   指定的索引id
     */
    public void singleWriteDoc(Map<String, Object> dataMap, String indexName, String indexId) {
        IndexRequest indexRequest = new IndexRequest(indexName).source(dataMap);
        if (StringUtils.hasText(indexId)) {
            indexRequest.id(indexId);
        }

        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            String index = response.getIndex();
            String id = response.getId();
            long version = response.getVersion();
            logger.info("index=[{}], id=[{}], version=[{}]", index, id, version);
        } catch (Exception e) {
            logger.error("writeSingleDoc error, ", e);
        }
    }

    /**
     * 批量写入文档
     *
     * @param dataList  写入的数据源
     * @param indexName 索引名称
     */
    public void bulkWriteDocs(List<Map<String, Object>> dataList, String indexName) {
        // 构建批量操作BulkRequestDUI西昂
        BulkRequest bulkRequest = new BulkRequest(indexName);
        for (Map<String, Object> data : dataList) {
            IndexRequest indexRequest = new IndexRequest().source(data);
            bulkRequest.add(indexRequest);
        }
        // 设置超时时间
        bulkRequest.timeout(TimeValue.timeValueSeconds(5));

        try {
            BulkResponse responses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (responses.hasFailures()) {
                logger.info("bulk write doc fail, message: {}", responses.buildFailureMessage());
            }
        } catch (IOException e) {
            logger.error("bulkWriteDocs error, ", e);
        }
    }
}
