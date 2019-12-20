package com.supergo.search.mapper;

import com.supergo.common.pojo.Goods;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName GoodsMapper
 * @Description TODO
 * @Author wesker
 * @Date 7/15/2019 5:12 PM
 * @Version 1.0
 **/
public interface GoodsMapper extends Mapper<Goods> {

    @Select("SELECT\n" +
            "\tCONCAT( CONCAT( MIN( price ), ',' ), MAX( price ) ) \n" +
            "FROM\n" +
            "\ttb_goods \n" +
            "WHERE\n" +
            "\tcategory3_id = #{needId}")
    String getPricePeriod(Long needId);


}
