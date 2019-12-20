package com.supergo.manager.controller;

import com.supergo.http.HttpResult;
import com.supergo.user.utils.UploadUtil;
import io.swagger.annotations.Api;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述:  图片上传
 *
 * @auther: jackhu
 * @date: 6/6/2019 14:47 AM
 */
@RestController
@RequestMapping("/upload")
@Api(value = "图片上传", protocols = "http", description = "图片上传")
public class UploadController {

    //注入图片服务器地址
    private String fast_dfs_url = "http://39.107.94.159/";

    /**
     * 需求：商家系统进行商品录入，上传商品图片
     */
    @RequestMapping(value = "pic", method = RequestMethod.POST)
    public HttpResult uploadPic(@RequestParam MultipartFile file) throws Exception {
        //指定TrackerServer地址
        //String tracker = "classpath:config/tracker.conf";
        //获取文件字节数组
        byte[] buffer = file.getBytes();
        //文件后缀
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //调用远程文件上传，传入到FastDFS
        String uploads = UploadUtil.upload(buffer, ext);
        //uploads[0]:文件组名
        //uploads[1]:文件存储路径
        //拼接文件访问路径
        //String url = "http://192.168.211.128/"+uploads[0]+"/"+uploads[1];
        String url = fast_dfs_url + uploads;
        return HttpResult.ok(url);
    }
}