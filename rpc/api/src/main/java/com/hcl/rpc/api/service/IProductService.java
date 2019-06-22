package com.hcl.rpc.api.service;

import com.hcl.rpc.api.bean.Product;

/**
 * @author: Hucl
 * @date: 2019/6/22 14:24
 * @description:
 */
public interface IProductService {

    Product getById(Integer id);
}
