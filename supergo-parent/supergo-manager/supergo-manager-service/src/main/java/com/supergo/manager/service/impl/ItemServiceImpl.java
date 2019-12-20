package com.supergo.manager.service.impl;

import com.supergo.common.pojo.Cart;
import com.supergo.common.pojo.Item;
import com.supergo.common.pojo.OrderItem;
import com.supergo.manager.mapper.ItemMapper;
import com.supergo.manager.service.ItemService;
import com.supergo.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * 功能描述：商品service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 15:43
*/
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public int updateStatus(Long[] ids, String status) {
        Example example = new Example(Item.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        //准备修改的数据
        Item item = new Item();
        item.setStatus(status);
        return itemMapper.updateByExampleSelective(item, example);
    }

    /**
     * 判断是否可以扣减库存，执行扣减库存
     *
     * @param cartList
     * @return
     */
    @Override
    public boolean deductionStock(List<Cart> cartList) {
        //检查是否可以扣减库存
        boolean idDeduction = true;//是否可以扣减

        for (Cart cart : cartList) {

            List<OrderItem> orderItemList = cart.getOrderItems();


            for (OrderItem orderItem : orderItemList) {

                //查询库存
                Item sku = itemMapper.selectByPrimaryKey(orderItem.getItemId());
                if (sku == null) {
                    idDeduction = false;
                    break;
                }
                if (!"1".equals(sku.getStatus())) {
                    idDeduction = false;
                    break;
                }
                if (sku.getNum().intValue() < orderItem.getNum().intValue()) {
                    idDeduction = false;
                    break;
                }

                //执行扣减
                if (idDeduction) {
                    for (OrderItem orderItem1 : orderItemList) {
                        itemMapper.deductionStock(orderItem1.getItemId(), orderItem.getNum());//扣减库存
                        itemMapper.addSaleNum(orderItem1.getItemId(), orderItem.getNum());//增加销量
                    }
                }


            }
        }
            return  idDeduction;
    }


}