package com.supergo.page.service.impl;

import com.alibaba.fastjson.JSON;
import com.supergo.common.pojo.Goods;
import com.supergo.common.pojo.GoodsDesc;
import com.supergo.common.pojo.Item;
import com.supergo.common.pojo.ItemCat;
import com.supergo.page.mapper.GoodsDescMapper;
import com.supergo.page.mapper.GoodsMapper;
import com.supergo.page.mapper.ItemCatMapper;
import com.supergo.page.mapper.ItemMapper;
import com.supergo.page.service.ItemPageService;
import com.supergo.user.utils.SSH2Util;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/17 15:46
 *
 ****/
@Service
public class ItemPageServiceImpl implements ItemPageService {

    private String path;

    public ItemPageServiceImpl() {
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        path = path.substring(1, path.indexOf("target")).concat("src/main/webapp/");
        this.path = path;
    }

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;

    /****
     * 实现创建静态页
     * @param goodsId
     * @return
     */
    @Override
    public boolean buildHtml(Long goodsId) {
        try {
            Map<String, Object> itemDescDataFromDb = this.getItemDescDataFromDb(goodsId);
            //第一步：创建一个 Configuration 对象，直接 new 一个对象。构造方法的参数就是 freemarker的版本号。
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //第四步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate("item.ftl");
            //第五步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。
            //第六步：创建一个 Writer 对象，一般创建一 FileWriter 对象，指定生成的文件名。
            File file = new File(this.path + goodsId + ".html");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            Writer writer = new BufferedWriter(new FileWriter(file));
            //第七步：调用模板对象的 process 方法输出文件。
            template.process(itemDescDataFromDb, writer);
            //第八步：关闭流
            writer.flush();
            writer.close();
            // 将文件传到阿里云服务器
            SSH2Util.getInstance().putFile(file.getAbsolutePath(), "/usr/data/item_pages");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /***
     * 删除静态页
     * @param id
     */
    @Override
    public void deleteByGoodsId(Long id) {
        //创建一个文件对象
        File file = new File(this.path + id + ".html");
        if (file.exists()) {
            //文件存在，则删除
            file.delete();
        }
        SSH2Util.getInstance().deleteFile("/usr/data/item_pages/" + id + ".html");
    }

    @Override
    public Map<String, Object> getDynamicPage(Long goodsId) {
        return this.getItemDescDataFromDb(goodsId);
    }

    public Map<String,Object> getItemDescDataFromDb(Long goodsId){
        Map<String, Object> map = new HashMap<>();
        // Goods、
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        //查询商品的分类  3个分类
        ItemCat itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id());
        ItemCat itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id());
        ItemCat itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
        // GoodsDesc、
        GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
        // List<Item>
        List<Item> itemList = skuList(goodsId);
        map.put("goods", goods);
        map.put("goodsDesc", goodsDesc);
        map.put("itemCat1", itemCat1);
        map.put("itemCat2", itemCat2);
        map.put("itemCat3", itemCat3);
        map.put("itemList", JSON.toJSONString(itemList));
        return map;
    }


    /****
     * 查询对应的SKU列表
     * @param goodsId
     * @return
     */
    private List<Item> skuList(Long goodsId) {
        //select * from tb_item where goods_id=? and status=1 order by is_default desc
        Example example = new Example(Item.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsId", goodsId);
        criteria.andEqualTo("status", "1");
        //设置排序
        example.orderBy("isDefault").desc();
        return itemMapper.selectByExample(example);
    }

}
