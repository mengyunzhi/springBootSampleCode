package com.mengyunzhi.demo.dynamicautowire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpeakFactoryImpl implements SpeakFactory {
    private final Map<Byte, SpeakService> servicesByCode = new HashMap<>();
    private CountryCode countryCode = CountryCode.CHINA;

    @Override
    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    @Autowired
    public void init(List<SpeakService> speakServices) {
        for (SpeakService speakService : speakServices) {
            this.register(speakService.getCode(), speakService);
        }
    }

    @Override
    public void register(Byte code, SpeakService speakService) {
        this.servicesByCode.put(code, speakService);
    }

    @Override
    public SpeakService getDynamicService() {
        return this.servicesByCode.get(this.countryCode.getCode());
    }
}