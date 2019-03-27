package com.mengyunzhi.demo.dynamicautowire;

public interface SpeakService {
    void sayHello();

    static String getBeanName() {
        return "DynamicServiceImpl";
    }

    Byte getCode();
}
