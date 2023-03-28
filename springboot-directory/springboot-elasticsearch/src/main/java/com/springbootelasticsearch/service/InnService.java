package com.springbootelasticsearch.service;

/**
 * @author Hu.ChangLiang
 * @date 2023/3/28 15:57
 */
public interface InnService {

    String index = "inn";

    /**
     * 查询所有的es数据
     * ```json
     * GET /inn/_search
     * {
     *  "_source": [         //只返回title和city字段
     *     "title",
     *     "city"
     *   ],
     *   "query": {
     *    "match_all": {    //查询所有文档
     *       "boost": 2     //设定所有文档的分值为2.0
     *     }
     *   }
     * }
     * ```
     */
    void matchAllSearch();

}
