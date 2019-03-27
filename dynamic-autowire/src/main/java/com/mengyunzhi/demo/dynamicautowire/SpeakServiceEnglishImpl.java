package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.stereotype.Component;

@Component
public class SpeakServiceEnglishImpl implements SpeakService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public Byte getCode() {
        return CountryCode.USA.getCode();
    }
}
