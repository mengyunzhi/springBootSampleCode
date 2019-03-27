package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.stereotype.Component;

/**
 * 美国话
 */
@Component
public class SayHelloServiceEnglishImpl implements SayHelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public Byte getCode() {
        return CountryCode.USA.getCode();
    }
}
