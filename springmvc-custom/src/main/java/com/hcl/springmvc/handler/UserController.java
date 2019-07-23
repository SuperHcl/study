package com.hcl.springmvc.handler;

import com.hcl.springmvc.annotation.Controller;
import com.hcl.springmvc.annotation.RequestMapping;
import com.hcl.springmvc.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hucl
 * @date: 2019/7/23 19:27
 * @description:
 */
@Controller
public class UserController {

    @RequestMapping("/query")
    @ResponseBody
    public Map<String, Object> query(int id, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Integer id) {
        return "ok" + id;
    }
}
