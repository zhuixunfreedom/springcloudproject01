package com.ylt.servicefunction.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AsyncDel {

    /**
     * @void 延时删除map中的验证码数据
     */
    @Async
    public void delMap(Map map, String phone) {
        try {
            Thread.sleep(60*1000);
            map.remove(phone);
        } catch (InterruptedException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
