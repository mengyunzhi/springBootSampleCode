package com.mengyunzhi.demo.dynamicautowire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SayHelloServiceImplTest {
    @Autowired
    SayHelloService sayHelloService;
    @Autowired
    SpeakFactory speakFactory;

    @Test
    public void sayHello() {
        // 默认说你好
        sayHelloService.sayHello();

        // 将国家设置为美国，再说你好
        speakFactory.setCountryCode(CountryCode.USA);
        sayHelloService.sayHello();

        // 将国家设置为中国，再说你好
        speakFactory.setCountryCode(CountryCode.CHINA);
        sayHelloService.sayHello();
    }
}