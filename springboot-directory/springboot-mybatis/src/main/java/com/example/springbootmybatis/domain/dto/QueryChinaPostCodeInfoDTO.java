package com.example.springbootmybatis.domain.dto;

import lombok.Data;

/**
 * @author Hu.ChangLiang
 * @date 2022/1/17 8:27 下午
 */
@Data
public class QueryChinaPostCodeInfoDTO {

    /**
     * all 所有
     * province 省
     * city 市
     * district 区
     */
    private String type;

    // 邮政编码
    private String code;


}
