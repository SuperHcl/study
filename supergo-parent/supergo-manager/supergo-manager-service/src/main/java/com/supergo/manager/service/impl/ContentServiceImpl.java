package com.supergo.manager.service.impl;

import com.supergo.manager.service.ContentService;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.manager.mapper.ContentMapper;
import com.supergo.manager.utils.RedisUtils;
import com.supergo.common.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能描述：内容service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:45
*/
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public List<Content> categoryContentList(Long categoryId) {
        //在缓存中找数据
        Object contents = redisUtils.hget("Content", String.valueOf(categoryId));
        //如果缓存中有数据，则直接返回缓存数据
        if(contents!=null){
            System.out.println("3.进入缓存============");
            return (List<Content>) contents;
        }
        //select * from tb_content where category_id=1
        Content content = new Content();
        content.setCategoryId(categoryId);
        List<Content> allContents = contentMapper.select(content);
        System.out.println("1.没有缓存数据=============");
        //存入Redis缓存
        if(allContents!=null && allContents.size()>0){
            System.out.println("2.开始缓存数据=============");
            redisUtils.hset("Content", String.valueOf(categoryId),allContents);
        }
        return allContents;
    }
}
