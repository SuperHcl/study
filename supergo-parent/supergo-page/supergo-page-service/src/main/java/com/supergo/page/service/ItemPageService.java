package com.supergo.page.service;

import java.util.Map;

/**
 * @ClassName ItemPageService
 * @Description TODO
 * @Author wesker
 * @Date 7/12/2019 1:28 PM
 * @Version 1.0
 **/
public interface ItemPageService {

    boolean buildHtml(Long goodsId);

    void deleteByGoodsId(Long id);

    Map<String, Object> getDynamicPage(Long goodsId);

}
