package com.lanou.daos;

import com.lanou.model.Orders;
import org.apache.ibatis.annotations.Param;

public interface OrdersMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer orderid);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    Orders selectByOrderNo(@Param("orderNo") String orderNo);

}