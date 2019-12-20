package com.supergo.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/27 18:05
 *
 ****/
@Controller
public class RedirectController {

    /***
     * 页面跳转
     * @param referer
     * @return
     */
    @RequestMapping(value = "/redirect")
    public String redirect(@RequestHeader(value = "Referer")String referer){
        if(!StringUtils.isEmpty(referer)){
            return  "redirect:"+referer;
        }
        return "/seckill-index.html";
    }

}
