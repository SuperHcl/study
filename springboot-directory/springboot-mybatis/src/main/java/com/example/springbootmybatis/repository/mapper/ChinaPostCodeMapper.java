package com.example.springbootmybatis.repository.mapper;

import com.example.springbootmybatis.domain.dto.QueryChinaPostCodeInfoDTO;
import com.example.springbootmybatis.repository.dao.ChinaPostCodeDAO;

import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2022/1/17 9:29 下午
 */
public interface ChinaPostCodeMapper {

    List<ChinaPostCodeDAO> queryChinaPostCodeList(QueryChinaPostCodeInfoDTO params);

}
