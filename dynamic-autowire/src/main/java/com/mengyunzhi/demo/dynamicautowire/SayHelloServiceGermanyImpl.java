package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.stereotype.Component;

@Component
public class SayHelloServiceGermanyImpl implements SayHelloService {
    @Override
    public void sayHello() {
        System.out.println("Hallo");
    }

    @Override
    public Byte getCode() {
        return CountryCode.GERMANY.getCode();
    }
}
