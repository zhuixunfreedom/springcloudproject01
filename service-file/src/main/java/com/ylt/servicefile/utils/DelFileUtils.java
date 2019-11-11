package com.ylt.servicefile.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DelFileUtils {
    /**
     * 文件夹内文件删除
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (!dir.exists())
            return false;
        if (dir.isDirectory()) {
            String[] childrens = dir.list();
            System.out.println(JSONObject.toJSON(childrens));
            // 递归删除目录中的子目录下
            for (String child : childrens) {
                System.out.println(child);
                File file1 = new File(dir, child);
                file1.delete();
            }
        }
        // 目录此时为空，可以删除
        return true;
    }
}
