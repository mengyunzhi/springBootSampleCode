package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说话工厂
 */
@Service
public class SayHelloFactoryImpl implements SayHelloFactory {
    /**
     * BEAN列表
     */
    private final Map<Byte, SayHelloService> servicesByCode = new HashMap<>();
    /**
     * 国家代码
     */
    private CountryCode countryCode = CountryCode.CHINA;

    @Override
    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 初始化
     *
     * @param speakServices spring获取到的所以实现了SpeakService的BEAN
     */
    @Autowired
    public void init(List<SayHelloService> speakServices) {
        for (SayHelloService speakService : speakServices) {
            this.register(speakService.getCode(), speakService);
        }
    }

    /**
     * 注册Bean
     *
     * @param code         代码
     * @param speakService BEAN
     */
    private void register(Byte code, SayHelloService speakService) {
        this.servicesByCode.put(code, speakService);
    }

    /**
     * 获取BEAN
     *
     * @return 对应的SpeakService BEAN
     */
    @Override
    public SayHelloService getSpeakService() {
        return this.servicesByCode.get(this.countryCode.getCode());
    }
}