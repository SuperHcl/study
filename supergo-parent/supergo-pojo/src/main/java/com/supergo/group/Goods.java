package com.supergo.group;

import com.supergo.common.pojo.TbGoods;
import com.supergo.common.pojo.TbGoodsDesc;
import com.supergo.common.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by on 2019/3/15.
 */
public class Goods implements Serializable{

    private TbGoods goods;

    private TbGoodsDesc goodsDesc;

    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
