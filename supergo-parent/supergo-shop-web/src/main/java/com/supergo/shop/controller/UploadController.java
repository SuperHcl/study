package com.supergo.shop.controller;

import com.supergo.common.pojo.Item;
import com.supergo.http.HttpResult;
import com.supergo.shop.config.FastDFSProperty;
import com.supergo.user.utils.UploadUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 功能描述:  文件上传,上传到FastDFS
 *
 * @param:
 * @return:
 * @auther: jeckhu
 * @date: 6/17/2019 9:37 AM
 */
@RestController
public class UploadController {

    /*//trackerserver地址
    @Value("${TRACKER_SERVER}")
    private String tracker_server;


    //文件上传拼接的域名
    @Value("${UPLOAD_IMAGE_DOMAIN}")
    private String upload_image_domain;*/


    /**
     * 功能描述: 文件上传方法
     *  URL：/upload.shtml    POST
     * 调用写好的FastDFS文件上传工具类方法实现文件上传
     * 拼接文件的路径，响应页面
     * @auther: jackhu
     * @date: 6/6/2019 14:48 AM
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传文件", notes = "接收接收上传的文件")
    @ApiResponses({
            @ApiResponse(code = 200, message = "上传文件成功"),
            @ApiResponse(code = 500, message = "上传文件失败,后台服务出现异常"),
            @ApiResponse(code = 401, message = "代表此操作无权限访问"),
            @ApiResponse(code = 400, message = "表示请求参数错误"),
    })
    public HttpResult upload(@RequestParam MultipartFile file) throws Exception {
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
        String url = FastDFSProperty.UPLOAD_IMAGE_DOMAIN + uploads;
        return HttpResult.ok(url);
    }


    public static void main(String[] args) {
        Item item = new Item();
        item.setTitle("小红");
        System.out.println("======MAIN======:" + item.getTitle());
        //调用外部方法
        updateItem(item);
        System.out.println("======MAIN======:" + item.getTitle());
    }

    public static void updateItem(Item uitem) {
        uitem.setTitle("U小白");
    }

}
