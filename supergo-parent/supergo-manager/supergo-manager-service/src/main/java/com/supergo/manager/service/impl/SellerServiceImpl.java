package com.supergo.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supergo.manager.service.SellerService;
import com.supergo.page.PageResult;
import com.supergo.service.base.impl.BaseServiceImpl;
import com.supergo.manager.mapper.SellerMapper;
import com.supergo.common.pojo.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 功能描述：商家service实现类
 * @Param
 * @Return
 * @Author jackhu
 * @Date 2019/7/23
 * @Time 17:00
*/
@Service
public class SellerServiceImpl extends BaseServiceImpl<Seller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;
    /**
     *
     * 功能描述: 
     *
     * @param:
     * @return: 
     * @auther: wesker
     * @date: 6/3/2019 12:18 PM
     */
    @Override
    public int updateStatus(String id, String status){
        //update tb-seller set status=? where id = ?
        Seller seller = new Seller();
        seller.setSellerId(id);
        seller.setStatus(status);
        return sellerMapper.updateByPrimaryKeySelective(seller);
    }

    @Override
    public PageResult getAllSellers(Integer page, Integer rows, Seller seller) {
//分页实现
        PageHelper.startPage(page,rows);
        //如果使用模糊搜索，则去掉注释，根据需要修改
        //构建条件
        Example example = new Example(Seller.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊搜索
        if(seller!=null){
            //公司名字不为空，则根据名字模糊搜索
            if(!StringUtils.isEmpty(seller.getName())){
                criteria.andLike("name","%"+seller.getName()+"%");
            }
            //店铺名称
            if(!StringUtils.isEmpty(seller.getNickName())){
                criteria.andLike("nickName","%"+seller.getNickName()+"%");
            }

            //审核状态
            if(!StringUtils.isEmpty(seller.getStatus())){
                criteria.andEqualTo("status",seller.getStatus());
            }
        }
        List<Seller> list = sellerMapper.selectByExample(example);
        //查询数据
        //List<Seller> list = sellerMapper.selectAll();
        PageInfo<Seller> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),
                pageInfo.getList(),rows);
    }


}
