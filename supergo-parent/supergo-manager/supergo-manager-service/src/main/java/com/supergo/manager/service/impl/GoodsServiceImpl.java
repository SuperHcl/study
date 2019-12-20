package com.supergo.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supergo.common.pojo.*;
import com.supergo.manager.mapper.*;
import com.supergo.manager.service.GoodsService;
import com.supergo.page.PageResult;
import com.supergo.service.base.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：产品service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 14:48
*/
@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements GoodsService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /***
     * 根据goodsid搜索商品集合
     * @param ids
     * @return
     */
    @Override
    public List<Item> getItemsByGoodsIds(List<Long> ids) {
        //select * from tb_item where goodsId in(1,2,3,555)
        Example example = new Example(TbItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("goodsId", ids);
        criteria.andEqualTo("status", "1");
        return itemMapper.selectByExample(example);
    }

    /***
     * 修改状态
     * @param ids:要修改的商品ID
     * @param status:修改的状态
     * @return
     */
    @Override
    public int updateStatus(List<Long> ids, String status) {
        //update status=? where id in(1,23,435,4356)
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        //构建条件
        criteria.andIn("id", ids);
        //准备修改的数据
        Goods goods = new Goods();
        goods.setAuditStatus(status);
        return goodsMapper.updateByExampleSelective(goods, example);
    }

    /**
     * 增加Goods
     *
     * @param goods
     * @return
     */
    @Override
    public int addGoods(Goods goods) {
        //添加Goods
        int acount = goodsMapper.insertSelective(goods);
        //添加GoodsDesc
        goods.getGoodsDesc().setGoodsId(goods.getId());     //设置主键ID
        goodsDescMapper.insertSelective(goods.getGoodsDesc());
        //添加Item对象
        this.insertGoodsItem(goods);
        //制造异常测试事务
        //System.out.println("受影响行数："+acount);
        //int q=10/0;
        return acount;
    }

    /***
     * 数据补全
     * @param goods
     * @param brand
     * @param seller
     * @param itemCat
     * @param now
     * @param item
     */
    public void itemParameterHandler(Goods goods, Brand brand, Seller seller, ItemCat itemCat, Date now, Item item) {
        //补全其他数据
        item.setGoodsId(goods.getId());     //商品SPU编号
        item.setSellerId(goods.getSellerId());//商家编号
        item.setCategoryid(goods.getCategory3Id());//商品分类编号：三级编号
        item.setCreateTime(now);        //创建日期
        item.setUpdateTime(now);        //修改日期
        //设置品牌信息
        item.setBrand(brand.getName());
        //设置商家信息
        item.setSeller(seller.getNickName());
        //设置三级分类
        item.setCategory(itemCat.getName());
        //获取商品第一张图片
        List<Map> imageMap = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageMap.size() > 0) {
            item.setImage(imageMap.get(0).get("url").toString());
        }
    }


    /***
     * 删除Goods
     * @param ids
     * @return
     */
    @Override
    public int deleteGoods(List<Long> ids) {
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        //return goodsMapper.deleteByExample(example);
        //修改状态，改成已删除   update tb_goods set is_delete =1 where id in(xx,xxx,xx,xxxx)
        Goods goods = new Goods();
        goods.setIsDelete("1");
        return goodsMapper.updateByExampleSelective(goods, example);
    }


    /***
     * 根据ID查询Goods
     * @param goods
     * @return
     */
    @Override
    public int updateGoods(Goods goods) {
        //修改Goods
        int mcount = goodsMapper.updateByPrimaryKeySelective(goods);
        //修改GoodsDesc
        goodsDescMapper.updateByPrimaryKeySelective(goods.getGoodsDesc());
        //删除之前已经存在的Item  delete from tb_item where goods_id = ?
        Item delItem = new Item();
        delItem.setGoodsId(goods.getId());
        itemMapper.delete(delItem);
        insertGoodsItem(goods);
        return mcount;
    }

    /****
     * 添加Item对象
     * @param goods
     */
    private void insertGoodsItem(Goods goods) {
        //增加新的Item
        //获取品牌信息
        Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
        //获取商家信息
        Seller seller = sellerMapper.selectByPrimaryKey(goods.getSellerId());
        //商品分类-三级分类
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
        //添加Item
        Date now = new Date();
        //启用规格
        if (goods.getIsEnableSpec() != null && "1".equals(goods.getIsEnableSpec())) {
            //循环补充Item数据
            for (Item item : goods.getItems()) {
                //商品名字
                String title = goods.getGoodsName();
                //获取Item的所有规格   {"机身内存":"16G","网络":"联通3G"}
                String spec = item.getSpec();
                Map<String, String> specMap = JSON.parseObject(spec, Map.class);
                //循环组装名字
                for (Map.Entry<String, String> entry : specMap.entrySet()) {
                    title = title + "   " + entry.getKey() + ":" + entry.getValue();
                }
                item.setTitle(title);
                //补全其他数据
                itemParameterHandler(goods, brand, seller, itemCat, now, item);
            }
            //批量增加
            itemMapper.insertList(goods.getItems());
        } else {
            //不启用规格时，只增加一个商品信息
            //这里只添加一个商品的SKU
            Item item = new Item();
            item.setTitle(goods.getGoodsName());
            //补全其他数据
            itemParameterHandler(goods, brand, seller, itemCat, now, item);
            //价格
            item.setPrice(goods.getPrice());
            //状态
            item.setStatus("1");
            //是否默认
            item.setIsDefault("1");
            //库存数量
            item.setNum(9999);
            //规格
            item.setSpec("{}");
            itemMapper.insertSelective(item);
        }
    }


    /***
     * 根据ID查询Goods
     * @param id
     * @return
     */
    @Override
    public Goods getByGoodsId(Long id) {
        //查询Goods
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        //查询GoodsDesc
        GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(goodsDesc);
        //查询Item数据   select * from tb_item where goods_id=?   没有删除的数据
        Item item = new Item();
        item.setGoodsId(id);
        List<Item> items = itemMapper.select(item);
        goods.setItems(items);
        return goods;
    }

    /***
     * 使用通用Mapper查询所有Goods
     * @return
     */
    @Override
    public PageResult getAllGoods(Integer pageNum, Integer size, Goods goods) {
        //分页实现
        PageHelper.startPage(pageNum, size);
        //如果使用模糊搜索，则去掉注释，根据需要修改
        //构建条件
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if (goods != null) {
            //根据名字模糊搜索
            if (!StringUtils.isEmpty(goods.getGoodsName())) {
                criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
            }
            //根据状态搜索
            if (!StringUtils.isEmpty(goods.getAuditStatus())) {
                criteria.andEqualTo("auditStatus", goods.getAuditStatus());
            }
            //查询商家自己的商品
            if (!StringUtils.isEmpty(goods.getSellerId())) {
                criteria.andEqualTo("sellerId", goods.getSellerId());
            }
        }
        //查询
        criteria.andCondition("(is_delete is null or is_delete='')");
        //模糊搜索
        List<Goods> list = goodsMapper.selectByExample(example);
        //查询数据
        //List<Goods> list = goodsMapper.selectAll();
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),
                pageInfo.getList(), size);
    }

    @Override
    public Map<String, Object> querySpecificationOption(Long typeTemplateId) {
        Map<String, Object> map = new HashMap<>();
        SpecificationOptionExample example = new SpecificationOptionExample();
        // 查询 TypeTemplate 信息
        TypeTemplateExample typeTemplateExample = new TypeTemplateExample();
        typeTemplateExample.createCriteria().andIdEqualTo(typeTemplateId);
        TbTypeTemplate tbTypeTemplate = typeTemplateMapper.selectOneByExample(typeTemplateExample);
        // 获取 specIds
        String specIds = tbTypeTemplate.getSpecIds();
        List<JSONObject> specIdsList = (List) JSONArray.parse(specIds);
        for (int i = 0; i < specIdsList.size(); i++) {
            JSONObject obj = specIdsList.get(i);
            // 获取每个id
            Integer id = (Integer) obj.get("id");
            String name = (String) obj.get("text");
            // 根据 id 获取 SpecificationOption
            example.createCriteria().andSpecIdEqualTo(id.longValue());
            List<SpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);
            example.clear();
            map.put(name, specificationOptions);
        }
        return map;
    }
}
