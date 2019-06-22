package com.hcl.product.service.impl;

import com.hcl.rpc.api.bean.Product;
import com.hcl.rpc.api.service.IProductService;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:28
 * @description:
 */
public class ProductServiceImpl implements IProductService {
    public Product getById(Integer id) {
        Product product = new Product();
        product.setId(1001);
        product.setName("iphone XR");
        product.setPrice(6999.0);
        return product;
    }
}
