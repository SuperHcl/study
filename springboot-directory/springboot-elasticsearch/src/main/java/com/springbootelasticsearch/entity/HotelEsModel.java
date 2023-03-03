package com.springbootelasticsearch.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 旅馆es模型
 *
 * @author Hu.ChangLiang
 * @date 2023/2/27 14:25
 */
@Getter
@Setter
@ToString
public class HotelEsModel extends BaseEsModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 城市
     */
    private String city;

    /**
     * 价格
     */
    private Double price;

    /**
     * 创建日期
     */
    private Date createTime;
}
