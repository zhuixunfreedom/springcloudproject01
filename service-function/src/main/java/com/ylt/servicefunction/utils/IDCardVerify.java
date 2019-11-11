package com.ylt.servicefunction.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ylt.servicefunction.bean.PersonBean;
import com.ylt.servicefunction.controller.FunctionController;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class IDCardVerify {
    public static String Authentication(String name,String id) throws Exception {
//        https://market.aliyun.com/products/57002003/cmapi00035152.html?spm=5176.730005.productlist.d_cmapi00035152.65b53524ogH1ij&innerSource=search_%E5%AE%9E%E5%90%8D%E8%AE%A4%E8%AF%81%20%E8%BA%AB%E4%BB%BD%E8%AF%81#sku=yuncode29152000014
        String host = "https://eid.shumaidata.com";
        String path = "/eid/check";
        String method = "POST";
        String appcode = "自己的阿里云代码";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();
        bodys.put("idcard", id);
        bodys.put("name", name);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    public static PersonBean getIDCardmsg(File imgFile) throws Exception {
        //API产品路径
        String host = "http://ai-market-ocr-person-id.icredit.link";
        String path = "/ai-market/ocr/personid";
        String method = "POST";
        //阿里云APPCODE
        String appcode = "自己的阿里云代码";
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();

        //内容数据类型，如：0，则表示BASE64编码；1，则表示图像文件URL链接
        //启用BASE64编码方式进行识别
        //内容数据类型转为BASE64编码
//        File imgFile = new File("D:\\idcard01.jpg");
        String imgBase64 = encodeImageToBase64(imgFile);
        bodys.put("AI_IDCARD_IMAGE", imgBase64);
        bodys.put("AI_IDCARD_IMAGE_TYPE", "0");

        //启用URL方式进行识别
        //内容数据类型是图像文件URL链接
//        bodys.put("AI_IDCARD_IMAGE", "图片URL链接");
//        bodys.put("AI_IDCARD_IMAGE_TYPE", "1");

        //身份证正反面，如：FRONT，身份证含照片的一面；BACK，身份证带国徽的一面
        bodys.put("AI_IDCARD_SIDE", "FRONT");
//        对象保存信息
        PersonBean personBean = null;
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            String json = EntityUtils.toString(response.getEntity());
//            System.out.println(json);
            personBean = JSONObject.parseObject(JSON.parseObject(json).getString("PERSON_ID_ENTITY"),PersonBean.class);
            System.out.println(personBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgFile.delete();
        return personBean;
    }







    /**
     * 将本地图片编码为base64
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String encodeImageToBase64(File file) throws Exception {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//        loggerger.info("图片的路径为:" + file.getAbsolutePath());
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("图片上传失败,请联系客服!");
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(data);
        return base64;//返回Base64编码过的字节数组字符串
    }
}
