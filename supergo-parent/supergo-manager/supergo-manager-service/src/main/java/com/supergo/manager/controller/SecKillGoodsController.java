package com.supergo.manager.controller;

import com.alibaba.fastjson.JSON;
import com.supergo.manager.mq.MessageSender;
import com.supergo.user.utils.MessageInfo;
import com.supergo.http.HttpResult;
import com.supergo.manager.service.SecKillGoodsService;
import com.supergo.page.PageResult;
import com.supergo.common.pojo.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
/**
 * 功能描述：秒杀产品增删改查
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/22
 * @Time 17:13
*/
@RestController
@RequestMapping("/seckillGoods")
public class SecKillGoodsController {

    /**
     * 功能描述：注入service对象
     * @Param
     * @Return
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:13
    */
    @Autowired
    private SecKillGoodsService secKillGoodsService;

    @Autowired
    private MessageSender messageSender;

    /**
     * 功能描述：修改产品审核
     * @Param [ids, status]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:14
    */
    @RequestMapping("/update/status")
    public HttpResult updateStatus(@RequestBody Long[] ids, @RequestParam("status") String status){
        int mcount = secKillGoodsService.updateStatus(ids, status);
        if(mcount>0){
            //修改成功
            if(status.equalsIgnoreCase("1")){
                //发送消息
                MessageInfo messageInfo = new MessageInfo(
                        1,
                        JSON.toJSONString(ids),
                        "TOPIC_SECKILL",
                        "TagsSeckill",
                        UUID.randomUUID().toString()
                );
                messageSender.sendMessage(messageInfo);
            }
            return HttpResult.ok("审核成功");
        }
        return HttpResult.error("审核失败");
    }


    /**
     * 功能描述：添加秒杀产品
     * @Param [seckillGoods]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:14
    */
    @RequestMapping("/add")
    public HttpResult add(@RequestBody(required = false) SeckillGoods seckillGoods){
        try {
            secKillGoodsService.add(seckillGoods);
            return HttpResult.ok("添加秒杀商品成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("添加秒杀商品失败!");
    }


    /**
     * 功能描述：删除秒杀产品
     * @Param [ids]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:14
    */
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody Long[] ids){
        try {
            secKillGoodsService.deleteByIds(ids);
            //删除成功
            return HttpResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //删除异常
            return HttpResult.error();
        }
    }


    /**
     * 功能描述：修改秒杀产品
     * @Param [seckillGoods]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:15
    */
    @RequestMapping("/update")
    public HttpResult update(@RequestBody(required = false) SeckillGoods seckillGoods) {
        try {
            secKillGoodsService.update(seckillGoods);
            return HttpResult.ok("修改秒杀商品成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpResult.error("修改秒杀商品失败!");
    }


    /**
     * 功能描述：根据ID查询秒杀产品
     * @Param [id]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:16
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestParam("id") Long id){
        return HttpResult.ok(secKillGoodsService.findOne(id));
    }


    /**
     * 功能描述：根据分页查询秒杀产品
     * @Param [pageNum, size, seckillGoods]
     * @Return com.jmyp.http.HttpResult
     * @Author jackhu
     * @Date 2019/7/22
     * @Time 17:16
    */
    @RequestMapping("/getAll")
    public HttpResult getAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("size") Integer size, @RequestBody(required = false) SeckillGoods seckillGoods){
        try {
            //分页查询
            PageResult result = secKillGoodsService.findPage(pageNum, size, seckillGoods);
            //查询成功
            return HttpResult.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            //查询异常
            return HttpResult.error();
        }
    }
}