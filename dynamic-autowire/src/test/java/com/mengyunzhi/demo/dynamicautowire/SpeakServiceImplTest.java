package com.mengyunzhi.demo.dynamicautowire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpeakServiceImplTest {
    @Autowired
    SpeakService speakService;
    @Autowired
    SayHelloFactory sayHelloFactory;

    @Test
    public void sayHello() {
        // 默认说你好
        speakService.sayHello();

        // 将国家设置为美国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.USA);
        speakService.sayHello();

        // 将国家设置为中国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.CHINA);
        speakService.sayHello();
    }


    @Test
    public void sayHello1() {
        // 默认说你好
        speakService.sayHello();

        // 将国家设置为美国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.USA);
        speakService.sayHello();

        // 将国家设置为德国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.GERMANY);
        speakService.sayHello();

        // 将国家设置为中国，再说你好
        sayHelloFactory.setCountryCode(CountryCode.CHINA);
        speakService.sayHello();
    }
}