package com.supergo.manager.service;

import com.supergo.common.pojo.Content;
import com.supergo.service.base.BaseService;

import java.util.List;

/**
 * 功能描述：内容service
 * @Param 
 * @Return 
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:45
*/
public interface ContentService extends BaseService<Content> {

    List<Content> categoryContentList(Long categoryId);
}
