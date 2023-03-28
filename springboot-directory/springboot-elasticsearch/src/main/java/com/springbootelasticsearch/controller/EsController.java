package com.springbootelasticsearch.controller;

import com.springbootelasticsearch.entity.HotelEsModel;
import com.springbootelasticsearch.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/27 15:11
 */
@RestController
@AllArgsConstructor
public class EsController {
    private final HotelService hotelService;

    /**
     * 根据title模糊查询
     *
     * @param title title
     * @return 匹配到的数据
     */
    @GetMapping("/getHotelByTitle")
    public List<HotelEsModel> getHotelByTitle(@RequestParam String title) {
        List<HotelEsModel> hotelFromTitle = hotelService.getHotelFromTitle(title);
        System.out.println(hotelFromTitle);
        return hotelFromTitle;
    }

    @PostMapping("/save/single")
    public String singleSave(@RequestBody HotelEsModel data) {
        hotelService.singleSave(data);
        return "success";
    }
    
    @PostMapping("/save/bulk")
    public String bulkSave(@RequestBody HotelEsModel data) {
        hotelService.bulkSave(data.getTitle(), data.getCity(), data.getPrice());
        return "success";
    }

    @DeleteMapping("/{index}/{id}")
    public void deleteByDocId(@PathVariable(value = "index") String index, @PathVariable(value = "id") String id) {
        hotelService.deleteById(index, id);
    }
}
