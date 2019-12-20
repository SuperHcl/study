package com.supergo.search.dao;

import com.supergo.common.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName ItemDao
 * @Description TODO
 * @Author wesker
 * @Date 7/12/2019 5:39 PM
 * @Version 1.0
 **/
@Repository
public interface ItemDao extends ElasticsearchRepository<Item,Long> {

    //goodsid  delete from tb_item where goodsid in(1,2,3)
    //deleteBygoodsIdIn
    /***
     * 根据goodsId删除索引数据
     * @param ids
     */
    void deleteByGoodsIdIn(List<Long> ids);

}
