package com.supergo.search.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemSearchService {

    String getCommonData(Integer typeId);

    List<String> suggest(String index, String suggest);

    Set<String> searchAssociation(String inputText);

    Map searchAll(Map searchMap);

    void deleteByGoodsIds(Long[] ids);

}
