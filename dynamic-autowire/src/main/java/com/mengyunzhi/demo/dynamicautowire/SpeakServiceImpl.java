package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 你好
 */
@Service
public class SpeakServiceImpl implements SpeakService {
    private final
    SayHelloFactory sayHelloFactory;  // 说话工厂

    @Autowired
    public SpeakServiceImpl(SayHelloFactory sayHelloFactory) {
        this.sayHelloFactory = sayHelloFactory;
    }

    @Override
    public void sayHello() {
        this.sayHelloFactory.getSayHelloService().sayHello();
    }
}
