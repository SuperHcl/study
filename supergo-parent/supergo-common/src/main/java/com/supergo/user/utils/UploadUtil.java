package com.supergo.user.utils;

import org.csource.fastdfs.*;

/***
 *
 * @Author:jackhu
 * @Description:jackhu
 * @date: 2019/3/6 17:09
 *  FastDFS文件上传
 ****/
public class UploadUtil {


    /****
     * 文件上传
     *      1.vfile:准备上传的文件全路径
     *      2.trackerserver:指定TrackerServer通信地址信息  Socket通信
     */
    /*public static String[] upload(String trackerserver, String vfile) throws Exception{
        //将classpath:替换成类路径
        trackerserver = getClasspath(trackerserver);
        vfile = getClasspath(vfile);


        //初始化加载TrackerServer服务地址通信信息
        ClientGlobal.init(trackerserver);

        //创建一个TrackerServer的客户端,目的是链接TrackerServer
        TrackerClient trackerClient = new TrackerClient();

        //通过TrackerServer客户端创建一个TrackerServer服务端对象。获取链接
        TrackerServer trackerServer = trackerClient.getConnection();

        //通过TrackerServer创建一个Storage客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        //通过Storage客户端上传文件
        //storageClient.upload_file("文件地址","后缀名","文件组名");
        String[] uploads = storageClient.upload_file(vfile, null, null);

        //获取文件上传信息   [0]:组名   [1]：存储路径
        for (String upload : uploads) {
            System.out.println(upload);
        }

        return uploads;
    }*/



    /****
     * 文件上传
     *      1.buffer:要上传的文件的字节数组
     *      2.trackerserver:指定TrackerServer通信地址信息  Socket通信
     *      3.上传的文件后缀名
     */
    public static String upload(byte[] bytes, String ext) throws Exception{
        //将classpath:换成类路径
        // trackerserver = getClasspath(trackerserver);
        //初始化加载TrackerServer服务地址通信信息
        // ClientGlobal.init(trackerserver);
        ClientGlobal.initByTrackers("39.107.94.159:22122");
        //创建一个TrackerServer的客户端,目的是链接TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerServer客户端创建一个TrackerServer服务端对象。获取链接
        TrackerServer trackerServer = trackerClient.getConnection();
        //通过TrackerServer创建一个Storage客户端
        StorageClient1 storageClient = new StorageClient1(trackerServer,null);
        String path = storageClient.upload_file1(bytes,ext ,null);
        //通过Storage客户端上传文件
        //storageClient.upload_file("文件地址","后缀名","文件组名");
        // String[] uploads = storageClient.upload_file(buffer, ext, null);
        //获取文件上传信息   [0]:组名   [1]：存储路径
        /*for (String upload : uploads) {
            System.out.println(upload);
        }*/
        return path;
    }


    /***
     * 替换classpath:
     * path  =  classpath:1.jpg
     * @param path
     * @return
     *//*
    public static String getClasspath(String path) throws Exception {
        //获取类路径
        // String classPath = UploadUtil.class.getResource("/").getPath();
        // InputStream in = UploadUtil.class.getClassLoader().getResourceAsStream("conf" + File.separator + "client.conf");
        // File file = new File("client.conf");
        // org.apache.commons.io.FileUtils.copyInputStreamToFile(in, file);
        //将classpath:换成类路径
        // path = path.replace("classpath:",classPath);
        // path = file.getAbsolutePath();
        ClassPathResource classPathResource = new ClassPathResource(path.replace("classpath:", ""));
        File file = classPathResource.getFile();
        String filePath = file.getAbsolutePath();
        return filePath;
    }*/
}
