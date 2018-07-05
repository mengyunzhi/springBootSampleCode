package com.mengyunzhi.springbootsamplecode.multiquery.service;
import java.util.Random;

/**
 * Created by panjie on 17/7/6.
 * 公共服务类
 */
public interface CommonService {
    // 获取长度为length的随机字符串
    static String getRandomStringByLength(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }
}
