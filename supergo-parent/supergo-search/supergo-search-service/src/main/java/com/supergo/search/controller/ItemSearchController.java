package com.supergo.search.controller;

import com.supergo.search.async.AsyncWork;
import com.supergo.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/search/item")
public class ItemSearchController {

    @Autowired
    private ItemSearchService itemSearchService;

    @Autowired
    private AsyncWork asyncWork;


    @RequestMapping("/getCommonData")
    public String getCommonData(@RequestParam("typeId") Integer typeId) {
        return itemSearchService.getCommonData(typeId);
    }

    @RequestMapping("/importDataToES")
    public void importDataToES() {
        asyncWork.importDataToES();
        System.out.println("服务调用完成，异步执行...");
    }

    @RequestMapping("/suggest")
    public List<String> suggest(@RequestParam("index") String index, @RequestParam("suggest") String suggest) {
        return itemSearchService.suggest(index, suggest);
    }

    @RequestMapping("/searchAssociation")
    public Set<String> searchAssociation(@RequestParam("inputText") String inputText) {
        return itemSearchService.searchAssociation(inputText);
    }

    @RequestMapping("/searchAll")
    public Map searchAll(@RequestBody Map searchMap) {
        return itemSearchService.searchAll(searchMap);
    }

}
