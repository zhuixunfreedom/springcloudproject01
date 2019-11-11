package com.ylt.servicefile.controller;

import com.ylt.servicefile.bean.FileTable;
import com.ylt.servicefile.dao.FileTabletDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/data")
public class UpAndDownController {

    private FileTabletDao fileTabletDao;
    @Autowired
    public void setFileTabletDao(FileTabletDao fileTabletDao) {
        this.fileTabletDao = fileTabletDao;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<FileTable> list(){
        Sort.Order order=new Sort.Order(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(0, 10,Sort.by(order));
        Page<FileTable> all = fileTabletDao.findAll(pageable);
        return all.getContent();
    }
    @Transactional
    @RequestMapping(value = "/delFile", method = RequestMethod.POST)
    public String delFile(@RequestParam("id")Long id){
        try {
            FileTable fileTable = fileTabletDao.findById(id).get();
            String path = new File("").getCanonicalPath()+
//                    开发环境
//                    "\\service-file\\src\\main\\resources\\static\\files\\" +
//                    正式环境
                    "\\service-file\\target\\classes\\static\\files\\" +
                    fileTable.getFileName();
            File file = new File(path);
            boolean delete = file.delete();
            if (!delete) return "failure";
            fileTabletDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest) request;
        Iterator<String> fileNames = multipartRequest.getFileNames();

        long  startTime=System.currentTimeMillis();
        while (fileNames.hasNext()){
            String fileName = fileNames.next();
            MultipartFile file = multipartRequest.getFile(fileName);
            assert file != null;
            fileName = file.getOriginalFilename();

//            对重名处理
            int count = fileTabletDao.getCount(fileName);
            if (count!=0){
                assert fileName != null;
                String[] s = fileName.split("\\.");
                fileName = s[0]+"("+String.valueOf(count)+")."+s[1];
            }
//            保存文件信息到数据库
            fileTabletDao.save(new FileTable(fileName,"./files/"+fileName, LocalDateTime.now()));

            String path = new File("").getCanonicalPath()+
//                    开发环境
//                    "\\service-file\\src\\main\\resources\\static\\files\\" +
//                    正式环境
                      "\\service-file\\target\\classes\\static\\files\\" +
//                    String.valueOf(UUID.randomUUID()).replace("-","") +
                    fileName;
            File newFile = new File(path);
            //通过MultipartFile的方法直接写文件
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
                return "failure";
            }

        }
        long  endTime=System.currentTimeMillis();
        System.out.println("采用file.Transto的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "success";
    }


    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String download(@RequestParam("url") String url) throws IOException {
        String[] split = url.split("/");
        String fileName =
//                String.valueOf(UUID.randomUUID()).replace("-","") +
                split[split.length-1];
        if (!fileName.contains(".")) return "failure";
        String savePath = new File("").getCanonicalPath()+
//                    开发环境
//                    "\\service-file\\src\\main\\resources\\static\\files\\" +
//                    正式环境
                    "\\service-file\\target\\classes\\static\\files";

        int count = fileTabletDao.getCount(fileName);
        if (count!=0){
            String[] s = fileName.split("\\.");
            fileName = s[0]+"("+String.valueOf(count)+")."+s[1];
        }

        downLoadFromUrl(url,fileName,savePath);
        fileTabletDao.save(new FileTable(fileName,"./files/"+fileName, LocalDateTime.now()));
        return "success";
    }


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //模拟浏览器，防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        fos.close();
        if(inputStream!=null){
            inputStream.close();
        }

//        System.out.println("info:"+url+" download success");

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }




}