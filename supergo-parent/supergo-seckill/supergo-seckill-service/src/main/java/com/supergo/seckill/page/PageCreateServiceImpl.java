package com.supergo.seckill.page;

import com.supergo.common.pojo.SeckillGoods;
import com.supergo.seckill.config.StaticProperty;
import com.supergo.seckill.mapper.SeckillGoodsMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 16:09
 *
 ****/
@Component
public class PageCreateServiceImpl {


    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean;


    /*****
     * 秒杀商品ID  此处建议使用多线程生成文件
     * @param seckillGoodsId
     * @return
     * @throws Exception
     */
    public boolean buildHtml(Long seckillGoodsId){
        try {
            //通过Freemarker生成Html
            Configuration configuration = freeMarkerConfigurationFactoryBean.createConfiguration();
            //构建一个模板对象
            Template template = configuration.getTemplate("seckill-item.ftl");
            //数据模型封装
            Map<String, Object> dataMap = new HashMap<String, Object>();
            //查询秒杀商品信息
            SeckillGoods seckillGoods = seckillGoodsMapper.selectByPrimaryKey(seckillGoodsId);
            dataMap.put("seckillGoods",seckillGoods);
            //准备输出   http://xxx/goodsid.html
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(StaticProperty.SECKILL_GOODS_PATH+seckillGoodsId+StaticProperty.SECKILL_GOODS_SUFFIX),"UTF-8"));
            template.process(dataMap,writer);
            //资源回收
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
