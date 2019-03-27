package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SayHelloServiceImpl implements SayHelloService {
    private final
    SpeakFactory speakFactory;  // 说话工厂

    @Autowired
    public SayHelloServiceImpl(SpeakFactory speakFactory) {
        this.speakFactory = speakFactory;
    }

    @Override
    public void sayHello() {
        this.speakFactory.getDynamicService().sayHello();
    }
}
