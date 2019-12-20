package com.supergo.search.mapper;

import com.supergo.common.pojo.Item;
import com.supergo.common.pojo.es.GoodsEs;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @ClassName ItemMapper
 * @Description TODO
 * @Author wesker
 * @Date 7/15/2019 5:00 PM
 * @Version 1.0
 **/
public interface ItemMapper extends Mapper<Item> {


    @Select("SELECT\n" +
            "\tg.brand_id AS brandId,\n" +
            "\tg.goods_name AS goodsName,\n" +
            "\tg.category3_id AS classify,\n" +
            "\tg.id AS goodsId,\n" +
            "\tgd.custom_attribute_items AS attr,\n" +
            "\tgd.specification_items AS spec,\n" +
            "\tg.price,\n" +
            "\ti.brand AS brandName,\n" +
            "\ti.category AS classifyName,\n" +
            "\ti.seller AS sellerName,\n" +
            "\ti.title,\n" +
            "\ti.image AS imagePath\n" +
            "FROM\n" +
            "\ttb_goods g\n" +
            "INNER JOIN tb_goods_desc gd ON g.id = gd.goods_id\n" +
            "INNER JOIN tb_item i ON i.goods_id = g.id")
    List<GoodsEs> queryDbToIndex();

    @Select("SELECT\n" +
            "\ti.title,\n" +
            "\ti.category_id AS classify,\n" +
            "\tg.goods_name AS goodsName\n" +
            "FROM\n" +
            "\ttb_item i\n" +
            "INNER JOIN tb_goods g ON i.goods_id = g.id")
    List<GoodsEs> selectMainIndexData();

}
