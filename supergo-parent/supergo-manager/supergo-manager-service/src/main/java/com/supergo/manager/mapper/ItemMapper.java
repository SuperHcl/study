package com.supergo.manager.mapper;

import com.supergo.common.pojo.Item;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

// @org.apache.ibatis.annotations.Mapper
public interface ItemMapper extends Mapper<Item>, InsertListMapper<Item> {
    void deductionStock(Long itemId, Integer num);

    void addSaleNum(Long itemId, Integer num);
}
