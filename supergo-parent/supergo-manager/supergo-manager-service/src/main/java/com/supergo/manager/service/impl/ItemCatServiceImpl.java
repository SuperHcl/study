package com.supergo.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supergo.common.pojo.ItemCat;
import com.supergo.common.pojo.ItemCatExample;
import com.supergo.http.HttpResult;
import com.supergo.manager.mapper.ItemCatMapper;
import com.supergo.manager.service.ItemCatSerice;
import com.supergo.page.PageResult;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.manager.utils.RedisUtils;
import com.supergo.user.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 功能描述：商品详情service实现类
 *
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:52
 */
@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatSerice {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<ItemCat> getByParentId(Long id) {
        List<ItemCat> tbItemCats = itemCatMapper.selectAll();
        for (ItemCat itemCat : tbItemCats) {
            //key:name
            //value:typeId(模板ID)
            redisUtils.hset("ItemCat", itemCat.getName(), itemCat.getTypeId());
        }
        //select * from tb_item_cat where parent_id=0;
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(id);
        return itemCatMapper.select(itemCat);
    }

    @Override
    public PageResult getByParentIdAndPage(Integer page, Integer rows, Long id) {
        PageHelper.startPage(page, rows);
        ItemCatExample example = new ItemCatExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<ItemCat> itemCats = itemCatMapper.selectByExample(example);
        PageInfo<ItemCat> info = new PageInfo<>(itemCats);
        return new PageResult(info.getTotal(),
                info.getList(), rows);
    }

    @Override
    public HttpResult categorysList() {
        // 从redis中取
        try {
            String json = (String) redisUtils.get("categoryList");
            if (!ObjectUtils.isEmpty(json)) {
                List<ItemCat> itemCats = JsonUtils.jsonToList(json, ItemCat.class);
                return HttpResult.ok(itemCats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //1，先查询顶级菜单
            //创建example对象
            Example example1 = new Example(ItemCat.class);
            Example.Criteria criteria1 = example1.createCriteria();
            //设置查询参数
            criteria1.andEqualTo("parentId", 0);
            //执行查询
            List<ItemCat> category1List = itemCatMapper.selectByExample(example1);
            //创建一个集合对象，封装菜单数据
            //List categoryList = new ArrayList();
            //查询二级菜单
            //3，循环顶级菜单，根据顶级菜单id查询子节点
            for (ItemCat category1 : category1List) {
                //创建example对象
                Example example2 = new Example(ItemCat.class);
                Example.Criteria criteria2 = example2.createCriteria();
                //设置查询参数
                criteria2.andEqualTo("parentId", category1.getId());
                //执行查询
                List<ItemCat> category2List = itemCatMapper.selectByExample(example2);
                //把集合放入category1
                category1.setChrildren(category2List);
                //5，循环二级菜单，根据二级菜单的id查询三级菜单
                for (ItemCat category2 : category2List) {
                    //创建example对象
                    Example example3 = new Example(ItemCat.class);
                    Example.Criteria criteria3 = example3.createCriteria();
                    //设置查询参数
                    criteria3.andEqualTo("parentId", category2.getId());
                    //执行查询
                    List<ItemCat> category3List = itemCatMapper.selectByExample(example3);
                    //放入category2对象
                    category2.setChrildren(category3List);
                }
            }
            // 放入redis
            redisUtils.set("categoryList", JsonUtils.objectToJson(category1List));
            return HttpResult.ok(category1List);
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败
            return HttpResult.error();
        }
    }
}
