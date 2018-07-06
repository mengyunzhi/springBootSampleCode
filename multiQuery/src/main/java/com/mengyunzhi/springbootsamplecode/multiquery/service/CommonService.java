package com.mengyunzhi.springbootsamplecode.multiquery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by panjie on 17/7/6.
 * 公共服务类
 */
public interface CommonService {
    Logger logger = LoggerFactory.getLogger(CommonService.class);

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

    // 将传入对象的所有字段，全部设置为null
    static void setFieldsToNull(Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(object, null);
            }
        } catch (IllegalAccessException e) {
            logger.warn("赋值为null导常");
            e.printStackTrace();
        }
    }
}
